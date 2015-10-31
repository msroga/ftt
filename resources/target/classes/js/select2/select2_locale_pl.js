/**
 * Select2 Polish translation.
 *
 * @author  Jan Kondratowicz <jan@kondratowicz.pl>
 * @author  Uriy Efremochkin <efremochkin@uriy.me>
 */
(function ($) {
    "use strict";

    $.extend($.fn.select2.defaults, {
        formatNoMatches: function () { return "Brak wynikĂłw"; },
        formatInputTooShort: function (input, min) { return "Wpisz jeszcze" + character(min - input.length, "znak", "i"); },
        formatInputTooLong: function (input, max) { return "Wpisana fraza jest za dĹuga o" + character(input.length - max, "znak", "i"); },
        formatSelectionTooBig: function (limit) { return "MoĹźesz zaznaczyÄ najwyĹźej" + character(limit, "element", "y"); },
        formatLoadMore: function (pageNumber) { return "Ĺadowanie wynikĂłwâŚ"; },
        formatSearching: function () { return "SzukanieâŚ"; }
    });

    function character (n, word, pluralSuffix) {
        return " " + n + " " + word + (n == 1 ? "" : n%10 < 5 && n%10 > 1 && (n%100 < 5 || n%100 > 20) ? pluralSuffix : "Ăłw");
    }
})(jQuery);