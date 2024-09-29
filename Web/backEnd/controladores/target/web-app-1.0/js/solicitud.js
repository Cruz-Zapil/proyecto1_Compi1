document.querySelector("#realizarConsultaBtn").addEventListener("click", function() {
    // Prevenir el comportamiento por defecto del formulario
    const form = document.getElementById('consultaForm');
    const formData = new FormData(form);
    const textoIngresado = formData.get('textoIngresado');
    
    // Realizar una solicitud GET al servlet
    fetch('solicitud?textoIngresado=' + encodeURIComponent(textoIngresado)) 
        .then(response => response.json())  // Convertir la respuesta en JSON
        .then(data => {
         

            // mensaje para mostrar 
            console.log(data.mensaje); // También puedes mostrarlo en el HTML si deseas
            
            // Mostrar errores en la tabla
            mostrarErrores(data.errores);
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

