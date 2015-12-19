package pl.ftt.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ftt.dao.*;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.ConnectionFilter;
import pl.ftt.domain.rel.ConnectionStationRelation;
import pl.ftt.domain.rel.ConnectionTagRelation;
import pl.ftt.service.IConnectionService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-11-12.
 */
@Service("connectionService")
public class ConnectionServiceImpl extends AbstractServiceImpl<Connection> implements IConnectionService
{
   @Resource
   private IConnectionDao connectionDao;

   @Resource
   private IStationDao stationDao;

   @Resource
   private IConnectionStationRelationDao stationRelationDao;

   @Resource
   private IConnectionTagRelationDao connectionTagRelationDao;

   @Override
   protected IAbstractDao<Connection> getDao()
   {
      return connectionDao;
   }

   @Override
   @Transactional
   public void save(Connection connection, List<ConnectionStationRelation> stations, List<Tag> tags)
   {
      save(connection);

      if (CollectionUtils.isNotEmpty(stations))
      {
         int index = 1;
         for (ConnectionStationRelation relation : stations)
         {
            relation.setIndex(index++);
         }
         stationRelationDao.saveList(stations);
      }
      if (CollectionUtils.isNotEmpty(tags))
      {
         List<ConnectionTagRelation> connectionTagRelations = new ArrayList<>();
         for (Tag tag : tags)
         {
            ConnectionTagRelation relation = new ConnectionTagRelation(connection, tag);
            connectionTagRelations.add(relation);
         }
         connectionTagRelationDao.saveList(connectionTagRelations);
      }
   }

   @Override
   @Transactional
   public void update(Connection connection, List<ConnectionStationRelation> stations, List<Tag> tags)
   {
      update(connection);

      List<ConnectionTagRelation> oldConnectionTagRelations = connectionTagRelationDao.findBy(connection);
      List<ConnectionStationRelation> oldStationRelations = stationRelationDao.find(connection);

      List<ConnectionStationRelation> stationsToDelete = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(stations))
      {
         for (ConnectionStationRelation old : oldStationRelations)
         {
            if (!stations.contains(old))
            {
               stationsToDelete.add(old);
            }
         }

         int index = 1;
         for (ConnectionStationRelation relation : stations)
         {
            relation.setIndex(index++);
            stationRelationDao.merge(relation);
         }

         if (CollectionUtils.isNotEmpty(stationsToDelete))
         {
            stationRelationDao.deleteList(stationsToDelete);
            stationRelationDao.flush();
         }
//         stationRelationDao.saveList(stations);
      }


      List<ConnectionTagRelation> toCreate = new ArrayList<>();
      List<ConnectionTagRelation> toDelete = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(tags))
      {
         for (Tag tag : tags)
         {
            if (!contains(tag, oldConnectionTagRelations))
            {
               ConnectionTagRelation relation = new ConnectionTagRelation(connection, tag);
               toCreate.add(relation);
            }
         }

         for (ConnectionTagRelation old : oldConnectionTagRelations)
         {
            if (!tags.contains(old.getTag()))
            {
               toDelete.add(old);
            }
         }
      }
      if (CollectionUtils.isNotEmpty(toDelete))
      {
         connectionTagRelationDao.deleteList(toDelete);
         connectionTagRelationDao.flush();
      }
      if (CollectionUtils.isNotEmpty(toCreate))
      {
         connectionTagRelationDao.saveList(toCreate);
      }
   }

   private boolean contains(Tag tag, List<ConnectionTagRelation> relations)
   {
      if (CollectionUtils.isNotEmpty(relations))
      {
         for (ConnectionTagRelation relation : relations)
         {
            if (relation.getTag().getName().equals(tag.getName()))
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   @Transactional(readOnly = true)
   public void find(ConnectionFilter filter)
   {
      connectionDao.find(filter);
   }
}
