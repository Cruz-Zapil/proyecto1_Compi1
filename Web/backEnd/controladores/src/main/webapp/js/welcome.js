    function mostrarSeccion(seccionId) {
        const seccion = document.getElementById(seccionId);
        const otrasSecciones = document.querySelectorAll('.container > div');
        otrasSecciones.forEach((sec) => sec.classList.add('hidden'));
        seccion.classList.remove('hidden');
    }
    
    function updateLineNumbers() {
        const codeArea = document.getElementById("codeArea");
        const lineNumbers = document.getElementById("lineNumbersContent");
    
        const lines = codeArea.value.split("\n").length;
        lineNumbers.innerHTML = Array.from({ length: lines }, (_, i) => i + 1).join("\n");
    }
    
    function syncScroll() {
        const codeArea = document.getElementById("codeArea");
        const lineNumbers = document.getElementById("lineNumbers");
    
        lineNumbers.scrollTop = codeArea.scrollTop;
    }
    
    function updateCursorPosition(event) {
        const codeArea = document.getElementById("codeArea");
        const cursorPosition = document.getElementById("cursorPosition");
    
        const textBeforeCursor = codeArea.value.substr(0, codeArea.selectionStart);
        const lines = textBeforeCursor.split("\n");
    
        const currentLine = lines.length;
        const currentColumn = lines[lines.length - 1].length + 1;
    
        cursorPosition.textContent = `Línea: ${currentLine}, Columna: ${currentColumn}`;
    }
    
    document.addEventListener("DOMContentLoaded", function () {
        updateLineNumbers();
    });


    const offcanvas = document.getElementById('demo');
    const dragHandle = document.querySelector('.drag-handle');

    let isDragging = false;
    let startY;
    let startHeight;

    // Detectar cuando comienza el arrastre
    dragHandle.addEventListener('mousedown', (e) => {
        isDragging = true;
        startY = e.clientY;
        startHeight = parseInt(window.getComputedStyle(offcanvas).height, 10);
        document.body.style.userSelect = 'none'; // Evita seleccionar texto mientras arrastras
    });

    // Detectar el movimiento mientras arrastras
    document.addEventListener('mousemove', (e) => {
        if (isDragging) {
            const newHeight = startHeight - (e.clientY - startY);
            offcanvas.style.height = `${Math.max(newHeight, 100)}px`; // Establecer nueva altura, con un mínimo
        }
    });

    // Detectar cuando termina el arrastre
    document.addEventListener('mouseup', () => {
        isDragging = false;
        document.body.style.userSelect = ''; // Restaurar la selección de texto
    });
    
