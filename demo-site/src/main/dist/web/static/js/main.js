(function () {
    $(document).ready(function () {

        $('.list-wrapper').each(function () {
            var $this = $(this);
            var $listBody = $this.find('.list-body');
            var $list = $listBody.children().first();

            $this.find('.list-heading').click(function () {
                $this.toggleClass("fold");
                $listBody.toggle();

                if (!$listBody.is(':hidden') && $list.children().length > 7) {
                    $this.find('.show-more').show();
                } else {
                    $this.find('.show-more').hide();
                    $this.find('.show-less').hide();
                    $listBody.css('overflow-y', 'hidden');
                }
            });

            if ($list.children().length > 7) {
                $listBody.css('overflow-y', 'hidden');

                if (!$listBody.is(':hidden')) {
                    $this.find('.show-more').show();
                }


                $this.find('.show-more').click(function () {
                    $listBody.css('overflow-y', 'scroll');
                    $this.find('.show-more').hide();
                    $this.find('.show-less').show();
                    $this.toggleClass('open');
                });

                $this.find('.show-less').click(function () {
                    $listBody.css('overflow-y', 'hidden');
                    $this.find('.show-more').show();
                    $this.find('.show-less').hide();
                    $this.toggleClass('open');
                })
            } else {
                $this.find('.show-more').hide();
                $this.find('.show-less').hide();
            }
        });

        function removeFilter(key, value) {
            var sourceURL = document.location.href;
            var rtn = sourceURL.split("?")[0],
                param,
                params = [],
                queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
            if (queryString !== "") {
                params = queryString.split("&");
                for (var i = params.length - 1; i >= 0; i -= 1) {
                    param = params[i].split("=")[0];
                    if (param === key) {
                        if (key === 'nav') {
                            var index = value.lastIndexOf('-');

                            if (index) {
                                params[i] = 'nav=' + value.substring(0, index);
                            } else {
                                params[i] = 'nav=';
                            }
                        } else if (value) {
                            params[i] = params[i].replace(encodeURIComponent(value).replace(/'/g, "%27").replace(/%2F/g, '/'), "");
                        } else {
                            params.splice(i, 1);
                        }
                        break;
                    }
                }
                rtn = rtn + "?" + params.join("&");
            }
            return rtn;
        }

        $('[data-exclude-filter]').each(function () {
            var $this = $(this);
            $this.attr('href', removeFilter($this.data("exclude-filter"), $this.data("filter-value")));
        });

        $('#toolbar').find('select').each(function () {
            var $this = $(this);

            $this.change(function () {
                var $this = $(this);
                document.location.href = $this.find("option:selected").val();
            });
        });

        $('.aside-header').click(function () {
            $(this).parents('.aside').toggleClass('active');
        });


        var storeDescriptions = $('.store-description').children();
        var toggleDescription = $('[name=toggleDescription]');
        if (storeDescriptions.length <= 1) {
            toggleDescription.hide();
        }

        storeDescriptions.each(function (index) {
            if (index == 0) return;
            $(this).toggle(false);
        });

        toggleDescription.click(function () {
            storeDescriptions.each(function (index) {
                if (index == 0) return;
                $(this).toggle();
            });
            $('[name=toggleDescription]').toggle();
        });
    });
})();