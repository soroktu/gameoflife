(function($, window, document) {

    $(document).ready(function() {
        initApp();
    });

    EDGE_LENGTH = 50;
    BOARD_ID = 1;

    function initApp() {
        drawMap(EDGE_LENGTH);
        paintStartingBoard();
        setupStartButton();
        setupLoadButton();
    }

    function drawMap(edgeLength) {
        var mainContainer = $('div#main');
        for (i = 0; i < edgeLength; i++) {
            for (j = 0; j < edgeLength; j++) {
                mainContainer.append('<div class="cell' + additionalClasses(i, j) + '" x=' + j + ' y=' + i + ' />');
            }
            mainContainer.append('<div class="clear" />');
        }
        $('div.cell').bind('click', function() {
            $(this).toggleClass('black');
        });
    }

    function additionalClasses(topEdge, leftEdge) {
        var classes = '';
        if (0 === topEdge) {
            classes += ' top-cell';
        }
        if (0 === leftEdge) {
            classes += ' left-cell';
        }
        return classes;
    }

    function paintStartingBoard() {

        paint(37, 37);
        paint(37, 38);
        paint(37, 39);
        paint(36, 39);
        paint(35, 38);
    }

    function paint(x, y) {
        $('.cell[x=' + x + '][y=' + y + ']').addClass('black');
    }

    function getJsonFromPaintedLiveCells() {
        var jsonObject = {};
        jsonObject.id = BOARD_ID;
        jsonObject.edgeLength = EDGE_LENGTH;
        jsonObject.liveCells = [];

        var liveCells = $('.cell.black');
        $.each(liveCells, function(index, cell) {
            var e = {};
            e.x = parseInt($(cell).attr('x'));
            e.y = parseInt($(cell).attr('y'));
            jsonObject.liveCells.push(e);
        });
        return JSON.stringify(jsonObject);
    }

    function setupStartButton() {
        $('input#start-button').bind('click', function() {
            sendCalculateAjaxRequest();
        })
    }

    function setupLoadButton() {
        $('input#load-button').bind('click', function() {
            sendLoadAjaxRequest();
        })
    }


    function sendCalculateAjaxRequest() {
        var paintedLiveCells = getJsonFromPaintedLiveCells();
        $.ajax({
            url : '/rest/gameoflife/calculate',
            type : 'post',
            data : paintedLiveCells,
            contentType: 'application/json; charset=utf-8',
            dataType : 'json',
            async : false,
            success : function(recalculatedBoard) {
                console.log("recalculatedBoard: " + recalculatedBoard, recalculatedBoard);
                repaintBoard(recalculatedBoard);
                setTimeout(sendCalculateAjaxRequest, 250);
            }
        });
    }

    function sendLoadAjaxRequest() {
        $.ajax({
            url : '/rest/gameoflife/load',
            type : 'get',
            contentType: 'application/json; charset=utf-8',
            dataType : 'json',
            async : false,
            success : function(recalculatedBoard) {
                console.log("recalculatedBoard: " + recalculatedBoard, recalculatedBoard);
                repaintBoard(recalculatedBoard);
            }
        });
    }
    function repaintBoard(board) {
        clearMap();
        paintBoard(board);
    }

    function paintBoard(board) {
        $.each(board.liveCells, function(index, cell) {
            paint(cell.x, cell.y);
        });
    }

    function clearMap() {
        $('.cell.black').removeClass('black');
    }

}(window.jQuery, window, document));