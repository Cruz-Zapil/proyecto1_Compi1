document.querySelector("#realizarConsultaBtn").addEventListener("click", function(event) {
    // Prevenir el comportamiento por defecto del formulario
    event.preventDefault();

    // Obtener el valor del textarea
    const textoIngresado = document.getElementById('codeArea').value;

    // Verificar si el valor se obtiene correctamente
    console.log('Texto ingresado:', textoIngresado);
        // Construir la URL manualmente
        const url = 'solicitud?textoIngresado=' + encodeURIComponent(textoIngresado); // Añade el parámetro de consulta



    fetch(url, {
        method: 'GET'
    })
    .then(response => response.json())  
    .then(data => {
        console.log(data.mensaje); 

        mostrarErrores(data.errores);
        mostrarMensajes(data.mensajes);  

        // Mostrar los mensajes en el offcanvas
        const mensajesList = document.getElementById('mensajesList');
        mensajesList.innerHTML = '';  // Limpiar mensajes previos

        data.mensajes.forEach(mensaje => {
            const li = document.createElement('li');
            li.classList.add('list-group-item');
            li.textContent = mensaje;
            mensajesList.appendChild(li);
        });

        // Mostrar el offcanvas de mensajes
        const mensajesCanvas = new bootstrap.Offcanvas(document.getElementById('mensajesCanvas'));
        mensajesCanvas.show();
    })
    .catch(error => console.error('Error al obtener los errores:', error));
});



function mostrarErrores(errores) {
    const erroresTableBody = document.querySelector('#erroresTable tbody');
    erroresTableBody.innerHTML = ''; // Limpiar cualquier contenido anterior

    // Iterar sobre la lista de errores y agregarlos a la tabla
    errores.forEach(error => {
        const fila = document.createElement('tr');
        
        fila.innerHTML = `
            <td>${error.tipo}</td>
            <td>${error.lexema}</td>
            <td>${error.descripcion}</td>
            <td>${error.linea}</td>
            <td>${error.columna}</td>
        `;
        
        erroresTableBody.appendChild(fila);
    });

      // Mostrar el offcanvas
      const offcanvas = new bootstrap.Offcanvas(document.getElementById('demo'));
      offcanvas.show();
    
}
