function cargarTrivias() {
    fetch('http://localhost:8080/web-app-1.0/list-trivias')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor: ' + response.status);
            }
            return response.json();  // Parseamos el JSON recibido
        })
        .then(trivias => {
            const tbody = document.getElementById('triviaTableBody');
            tbody.innerHTML = ''; // Limpiar contenido anterior

            // Iterar sobre las trivias recibidas y agregarlas a la tabla
            trivias.forEach(trivia => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${trivia.ID_TRIVIA}</td>
                    <td>${trivia.NOMBRE}</td>
                    <td>${trivia.TIEMPO}</td>
                    <td>${trivia.USUARIO_CREACION}</td>
                    <td>${trivia.TEMA}</td>
                    <td>${trivia.FECHA_CREACION}</td>
                `;
                tbody.appendChild(fila); // Añadir fila a la tabla
            });
        })
        .catch(error => {
            console.error('Error al cargar las trivias:', error);
            alert('Hubo un error al cargar las trivias. Ver la consola para más detalles.');
        });
}

window.onload = cargarTrivias;  // Cargar las trivias cuando la página haya cargado
