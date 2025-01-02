function drawBoard() {
    var bw = 400; // Canvas width
    var bh = 400; // Canvas height
    
    var centerX = bw / 2;
    var centerY = bh / 2;
    var radius = 100; // Distance from the center to each cell center
    var numCells = 5;

    gridContext.strokeStyle = "black";
    gridContext.lineWidth = 1;

    for (var i = 0; i < numCells; i++) {
        var angle = (2 * Math.PI / numCells) * i - Math.PI / 2;
        var cellCenterX = centerX + radius * Math.cos(angle);
        var cellCenterY = centerY + radius * Math.sin(angle);

        gridContext.beginPath();
        gridContext.rect(
            cellCenterX - cellSize / 2,
            cellCenterY - cellSize / 2,
            cellSize,
            cellSize
        );
        gridContext.stroke();
    }
}

function addHighlightCanvasClickListener() {
    highlightCanvas.addEventListener('click', function (event) {
        var rect = highlightCanvas.getBoundingClientRect();
        var x = event.x - rect.left;
        var y = event.y - rect.top;

        var closestIndex = -1;
        var closestDistance = Infinity;

        for (var i = 0; i < 5; i++) {
            var angle = (2 * Math.PI / 5) * i - Math.PI / 2;
            var cellCenterX = 200 + 100 * Math.cos(angle); // Adjusted for canvas center
            var cellCenterY = 200 + 100 * Math.sin(angle);

            var distance = Math.sqrt(
                Math.pow(x - cellCenterX, 2) + Math.pow(y - cellCenterY, 2)
            );

            if (distance < closestDistance && distance <= cellSize / 2) {
                closestDistance = distance;
                closestIndex = i;
            }
        }

        if (closestIndex !== -1) {
            highlightCell(closestIndex);
        }
    }, false);
}

function highlightCell(index) {
    var angle = (2 * Math.PI / 5) * index - Math.PI / 2; // Adjust to start at 12:00
    var cellCenterX = 200 + 100 * Math.cos(angle);
    var cellCenterY = 200 + 100 * Math.sin(angle);

    highlightContext.beginPath();
    highlightContext.clearRect(0, 0, 400, 400);
    highlightContext.strokeStyle = "maroon";
    highlightContext.lineWidth = 5;
    highlightContext.rect(
        cellCenterX - cellSize / 2,
        cellCenterY - cellSize / 2,
        cellSize,
        cellSize
    );
    highlightContext.stroke();
}

function setCellText(index, text) {
    var angle = (2 * Math.PI / 5) * index - Math.PI / 2; // Adjust to start at 12:00
    var cellCenterX = 200 + 100 * Math.cos(angle);
    var cellCenterY = 200 + 100 * Math.sin(angle);

    textContext.beginPath();
    textContext.clearRect(
        cellCenterX - cellSize / 2 + 1,
        cellCenterY - cellSize / 2 + 1,
        cellSize - 2,
        cellSize - 2
    );
    textContext.fillStyle = "indigo";
    textContext.font = "bold " + fontSize + "px Arial";
    textContext.textAlign = 'center';
    textContext.textBaseline = 'middle';
    textContext.fillText(text, cellCenterX, cellCenterY);
    textContext.stroke();
    generateWords("CIRCLE_GRID");
}
