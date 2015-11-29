/**
 * Created by Marek on 2015-05-28.
 */
$(document).ready(function () {
    pageSetUp();
});

function initCKEditor(textarea_id, language) {

    CKEDITOR.replace(textarea_id, {
        toolbar: [
            { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
            { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Paste', 'PasteText', '-', 'Undo', 'Redo' ] },
            { name: 'links', items: [ 'Link', 'Unlink'] },
            { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
            { name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'SpecialChar' ] },
            { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] },
            { name: 'tools', items: [ 'Maximize' ] },
            { name: 'styles', items: [ 'Styles', 'Format' ] },
            { name: 'colors', items: ['TextColor', 'BGColor' ]}
        ],
        language: language,
        defaultLanguage: language,
        basicEntities: true,
        entities: false,
        entities_latin: false,
        entities_greek: false,
        startupFocus: true,
        removePlugins: 'tabletools, contextmenu',
        defaultLanguage: 'pl',
        extraPlugins: 'panelbutton,colorbutton'
    });
}

function initCKEditorAutoGrow(textarea_id, language) {

    CKEDITOR.replace(textarea_id, {

        toolbar: [
            { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
            { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: ['Paste', 'PasteText', '-', 'Undo', 'Redo' ] },
            { name: 'links', items: [ 'Link', 'Unlink'] },
            { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
            { name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'SpecialChar' ] },
            { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] },
            { name: 'tools', items: [ 'Maximize' ] },
            { name: 'styles', items: [ 'Styles', 'Format' ] },
            { name: 'colors', items: ['TextColor', 'BGColor' ]}

        ],
        language: language,
        defaultLanguage: language,
        basicEntities: true,
        entities: false,
        entities_latin: false,
        entities_greek: false,
        startupFocus: true,
        // Remove the Resize plugin as it does not make sense to use it in conjunction with the AutoGrow plugin.
        removePlugins: 'resize, tabletools, contextmenu',
        autoGrow_onStartup: true,
        extraPlugins: 'panelbutton,colorbutton,autogrow'
    });
}

function initCKEditorWithKeys(textarea_id, language) {

    CKEDITOR.replace(textarea_id, {

        toolbar: [
            { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
            { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Paste', 'PasteText', '-', 'Undo', 'Redo' ] },
            { name: 'links', items: [ 'Link', 'Unlink'] },
            { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
            { name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'SpecialChar' ] },
            { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] },
            { name: 'tools', items: [ 'Maximize' ] },
            { name: 'strinsert', items: [ 'strinsert']},
            { name: 'styles', items: [ 'Styles', 'Format' ] },
            { name: 'colors', items: ['TextColor', 'BGColor' ]}

        ],
        language: language,
        defaultLanguage: language,
        basicEntities: true,
        entities: false,
        entities_latin: false,
        entities_greek: false,
        startupFocus: true,
        // Remove the Resize plugin as it does not make sense to use it in conjunction with the AutoGrow plugin.
        removePlugins: 'resize , tabletools, contextmenu',
        autoGrow_onStartup: true,
        extraPlugins: 'panelbutton,colorbutton,autogrow,strinsert'
    });
}

function initCKEditorReadOnly(textarea_id, language) {

    CKEDITOR.replace(textarea_id, {

        toolbar: [],
        language: language,
        defaultLanguage: language,
        basicEntities: true,
        entities: false,
        entities_latin: false,
        entities_greek: false,
        // Remove the Resize plugin as it does not make sense to use it in conjunction with the AutoGrow plugin.
        removePlugins: 'resize, tabletools, contextmenu',
        autoGrow_onStartup: true,
        extraPlugins: 'panelbutton,colorbutton,autogrow'
    });
}

//function initMultiselectScrollTop() {
//
//    var elementIndex = null;
//    var indexes = null;
//
//    $('.select2.expand-select2').on('select2-close', function () {
//        $(this).data('select2').nextSearchTerm = undefined;
//    });
//
//    $('.select2.expand-select2').on("select2-selecting", function (choice, object) {
//        var values = $(this).val();
//        var selectedValue = choice.val;
//        indexes = [];
//        $(this).find('option').each(function (index) {
//            var value = $(this).val();
//            if (value == selectedValue) {
//                elementIndex = index;
//                indexes.push(index)
//            } else if ($.inArray(value, values) != -1) {
//                indexes.push(index);
//            }
//        });
//    });
//
//    //after selected element of multiselect is drawing again, so only can be scroll top execute
//    $('.select2.expand-select2').on('change', function (e) {
//        if (elementIndex != null) {
//            var selectElement = findIndex(elementIndex, indexes);
//            var element = $('.select2-drop-active > ul li').eq(selectElement);
//        }
//        if (element != undefined && element.position() != undefined) {
//            // max-width of select is 200px, take half of that to center it
//            $('.select2-drop-active > ul').scrollTop(element.position().top - 100);
//        }
//    })
//
//    function findIndex(object, indexes) {
//        if ($.inArray(object, indexes) != -1) {
//            if (object == 1) {
//                return 1;
//            }
//            return findIndex(object - 1, indexes);
//        } else {
//            return object;
//        }
//    }
//}