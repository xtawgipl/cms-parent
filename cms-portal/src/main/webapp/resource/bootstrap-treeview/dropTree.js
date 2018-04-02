//create zhangjj



var DropSelect = function(ele, opt) {
    this.$element = ele,
        this.defaults = {
            'dataUrl': '',
            'data': {}
        },
        this.options = $.extend({}, this.defaults, opt)
};
//DropSelect
DropSelect.prototype = {
    init: function() {
        var self = this;
        self.$element.empty();//清空option
        $.ajax({
            type: "POST",
            url: self.options.dataUrl,
            data: self.options.data,
            async: true,
            timeout: 5000,
            dataType: 'json',
            beforeSend: function (xhr) {
                console.log("ajax beforeSend");
                console.log(xhr);
            },
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                console.log("ajax success---");
                $.each(data, function (index, item) {
                    self.$element.append("<option value='"+item.id+"'>"+item.text+"</option>");
                });
            },
            error: function (xhr, testStatus) {
                console.log("ajax error");
            },
            complete: function () {
                console.log("ajax complete");
            }
        });
    }
};


var DropTree = function(ele, opt) {
    this.$element = ele,
        this.defaults = {
            'xOffset': 0,
            'yOffset': 0,
            'treePanle': '',
            'tree': '',
            'treeClose': '',
            'dataUrl': '',
            'callback': function () {
                
            }
        },
        this.options = $.extend({}, this.defaults, opt)
};
//DropTree
DropTree.prototype = {
    init: function() {
        var self = this;
        $(window).resize(function() {
            $("#" + self.options.treePanle)
                .css("position", "absolute")
                .css("left", self.$element.offset().left + self.options.xOffset + "px")
                .css("top", self.$element.offset().top + self.options.yOffset +"px")
                .css("width", self.$element.width() + 25 + "px");
        });
        $.ajax({
            type: "POST",
            url: self.options.dataUrl,
            async: true,
            timeout: 5000,
            dataType: 'json',
            beforeSend: function (xhr) {
                console.log("ajax beforeSend");
                console.log(xhr);
            },
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                console.log("ajax success---");
                $('#' + self.options.tree).treeview({
                    data: data,
                    onNodeSelected: function(event, node) {
                        console.log("node.text = " + node.text);
                        console.log("node.id = " + node.id);
                        self.$element.val(node.text);
                        self.options.callback(node.id);
                    }
                });
            },
            error: function (xhr, testStatus) {
                console.log("ajax error");
            },
            complete: function () {
                console.log("ajax complete");
            }
        });


        this.$element.click(function (){
            console.log("" + self.options.treePanle);
            $("#" + self.options.treePanle)
                .css("position", "absolute")
                .css("left", parseInt(self.$element.offset().left) + self.options.xOffset + "px")
                .css("top", self.$element.offset().top + self.options.yOffset +"px")
                .css("width", self.$element.width() + 25 + "px").show(200);
            console.log("111" + $("#" + self.options.treePanle).css("display"));
        });

        $("#" + this.options.treeClose).click(function (){
            $("#" + self.options.treePanle).hide(200);
        });
    }
};


$.fn.DropSelect = function(options) {
    var dropSelect = new DropSelect(this, options);
    //调用其方法
    dropSelect.init();
};

$.fn.DropTree = function(options) {
    var dropTree = new DropTree(this, options);
    //调用其方法
    dropTree.init();
};

