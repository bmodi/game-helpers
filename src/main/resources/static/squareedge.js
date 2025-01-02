function drawBoard() {
    var bw = 400; // Canvas width
    var bh = 400; // Canvas height
    var gridSize = 7; // Total size of the grid in terms of cells
    var spacing = bw / gridSize; // Spacing between cells

    gridContext.strokeStyle = "black";
    gridContext.lineWidth = 1;

    // Top edge
    for (var i = 2; i <= 4; i++) {
        var x = i * spacing;
        var y = spacing;
        drawCell(x, y);
    }

    // Right edge
    for (var i = 2; i <= 4; i++) {
        var x = bw - spacing;
        var y = i * spacing;
        drawCell(x, y);
    }

    // Bottom edge
    for (var i = 2; i <= 4; i++) {
        var x = i * spacing;
        var y = bh - spacing;
        drawCell(x, y);
    }

    // Left edge
    for (var i = 2; i <= 4; i++) {
        var x = spacing;
        var y = i * spacing;
        drawCell(x, y);
    }
}

function drawCell(x, y) {
    gridContext.beginPath();
    gridContext.rect(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
    gridContext.stroke();
}

function addHighlightCanvasClickListener() {
    highlightCanvas.addEventListener('click', function (event) {
        var rect = highlightCanvas.getBoundingClientRect();
        var x = event.x - rect.left;
        var y = event.y - rect.top;

        var closestX = Math.round(x / (400 / 7)) * (400 / 7);
        var closestY = Math.round(y / (400 / 7)) * (400 / 7);

        if (
            (closestY === 400 / 7 || closestY === 6 * (400 / 7) || // Top or bottom edge
                closestX === 400 / 7 || closestX === 6 * (400 / 7)) && // Left or right edge
            ((closestX >= 2 * (400 / 7) && closestX <= 4 * (400 / 7)) || // Valid column range
                (closestY >= 2 * (400 / 7) && closestY <= 4 * (400 / 7))) // Valid row range
        ) {
            highlightCell(closestX, closestY);
        }
    });
}

function highlightCell(x, y) {
    highlightContext.beginPath();
    highlightContext.clearRect(0, 0, 400, 400);
    highlightContext.strokeStyle = "maroon";
    highlightContext.lineWidth = 5;
    highlightContext.rect(x - cellSize / 2, y - cellSize / 2, cellSize, cellSize);
    highlightContext.stroke();
}

function setCellText(row, col, text) {
    var spacing = 400 / 7;
    var x = col * spacing;
    var y = row * spacing;

    textContext.beginPath();
    textContext.clearRect(x - cellSize / 2 + 1, y - cellSize / 2 + 1, cellSize - 2, cellSize - 2);
    textContext.fillStyle = "indigo";
    textContext.font = "bold " + fontSize + "px Arial";
    textContext.textAlign = 'center';
    textContext.textBaseline = 'middle';
    textContext.fillText(text, x, y);
    textContext.stroke();
    generateWords("SQUARE_GRID");
}
