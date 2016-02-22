$(function () {

    var $addStoreForm = $('.country-home-add-store-form');
    var $addStoreSuccess = $('.country-home-add-store-success');

    $('.btn-add-store').click(function () {
        var storeURL = $('#storeURL').val();

        if (storeURL) {
            $.get('/store/index/create?storeUri=' + storeURL, function () {
                $addStoreForm.hide();
                $addStoreSuccess.show();
            });
        }

        return false;
    });

    $('.btn-add-store-again').click(function () {
        $('#storeURL').val('');
        $addStoreForm.show();
        $addStoreSuccess.hide();

        return false;
    });
    $.get('/iguamacustomer/index/message/?type=home', function (data) {
        $(".loading").remove();
        if (data.msg == null) {
            $('.country-home-page-header').remove();
        } else {
            $(".country-home-page-header-msg").append(data.msg);
        }
    }).fail(function () {
        $(".loading").remove();
        $('.country-home-page-header').remove();
    });
    $('.store-image-container').each(function () {
        $(this).mouseenter(function () {
            var $storeLikeLink = $(this).children('.store-likes-container').children('a');
            if ($storeLikeLink.length < 1) {
                return false;
            }
            if (!$storeLikeLink.data("store-id")) {
                $.get('/storeinfo?vendorid=' + $(this).data("vendor-number"), function (data) {
                    $storeLikeLink.data("store-id", data.store_id).data("follow", false);
                    $storeLikeLink.click(function () {
                        var $this = $(this);
                        var $imageFollow = $this.children(".image-follow");
                        if ($this.data("follow")) {
                            $.get('/store/index/unfollow?storeId=' + $this.data('store-id'), function () {
                                $imageFollow.attr('src', '/static/img/store-unlike.png');
                                $this.data("follow", false)
                            });
                        } else {
                            $.get('/store/index/follow?storeId=' + $this.data('store-id'), function () {
                                $imageFollow.attr('src', '/static/img/store-like.png');
                                $this.data("follow", true)
                            });
                        }
                    });
                });
            }
            $(this).children('.store-likes-container').fadeIn();
        }).mouseleave(function () {
            $(this).children('.store-likes-container').fadeOut();
        });
    });
});