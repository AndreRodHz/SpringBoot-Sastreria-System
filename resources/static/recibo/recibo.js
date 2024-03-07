// Define una función para buscar el cliente
function buscarCliente() {
    var numeroDocC = document.getElementById("numeroDocC").value;

    // Crea un formulario temporal
    var form = document.createElement("form");
    form.method = "post";
    form.action = "/recibo/buscar_cliente"; // Cambia esto a la URL correcta

    // Crea un campo de entrada oculto con el valor necesario
    var input = document.createElement("input");
    input.type = "text";
    input.name = "numeroDocC";
    input.value = numeroDocC; // Usa la variable definida arriba

    // Agrega el campo de entrada al formulario
    form.appendChild(input);

    // Agrega el formulario al cuerpo del documento y envíalo
    document.body.appendChild(form);
    form.submit();

    // Elimina el formulario temporal
    document.body.removeChild(form);
}

function validarCampos() {
	var idCliente = document.getElementById("idCliente").value;
    var tablaCarrito = document.querySelector('.table tbody'); // Obtener el tbody de la tabla del carrito
    var filasCarrito = tablaCarrito.getElementsByTagName('tr'); // Obtener todas las filas dentro del tbody
    
	if (idCliente === '') {
        alert("El campo ID del Cliente está vacío. Ingresa un cliente para continuar.");
        return false; // Evita que se envíe el formulario
    }
    
    if (filasCarrito.length === 0) {
        alert("El carrito está vacío. Agrega productos para continuar.");
        return false; // Evita que se continúe con la acción
    }
    

    return true; // Permite enviar el formulario si al menos un checkbox está activado
}


// Obtener una referencia al campo del número de comprobante
const numeroComprobanteInput = document.getElementById('numeroComprobanteRecibo');

// Agregar un evento 'blur' para validar el número de comprobante
numeroComprobanteInput.addEventListener('blur', function() {
    // Obtener el valor ingresado en el campo del número de comprobante
    const numeroComprobante = numeroComprobanteInput.value;

    // Realizar la petición al servidor para verificar el comprobante
    verificarComprobante(numeroComprobante);
});

// Función para verificar el comprobante haciendo la solicitud al backend
function verificarComprobante(numeroComprobante) {
    fetch(`/recibo/verificar_comprobante?numeroComprobante=${numeroComprobante}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Manejar la respuesta de la petición
            console.log(data); // Aquí puedes realizar acciones con la respuesta recibida
            if (data.existe) {
                // El comprobante existe
                alert('El número de comprobante ya existe en la base de datos.');
                numeroComprobanteInput.value = ''; // Limpiar el campo si es inválido
            }
        })
        .catch(error => {
            // Manejar errores de la petición
            console.error('There has been a problem with your fetch operation:', error);
        });
}


// Espera a que el documento esté completamente cargado
document.addEventListener("DOMContentLoaded", function() {
    // Obtener el select y el campo númeroComprobanteRecibo por su ID
    const idComprobanteSelect = document.getElementById("idComprobante");
    const numeroComprobanteRecibo = document.getElementById("numeroComprobanteRecibo");

    // Llamar a la función para generar el número de comprobante inicial al cargar la página
    actualizarNumeroComprobante();

    // Agregar un evento de cambio al select
    idComprobanteSelect.addEventListener("change", function() {
        // Llamar a la función cada vez que cambie la opción del select para actualizar el número de comprobante
        actualizarNumeroComprobante();
    });

    // Función para actualizar el valor del campo numeroComprobanteRecibo
    function actualizarNumeroComprobante() {
        // Obtener el valor seleccionado del select
        const selectedOption = idComprobanteSelect.options[idComprobanteSelect.selectedIndex];
        const selectedIdComprobante = selectedOption.value;

        // Llamar a la función para generar el número de comprobante correspondiente
        obtenerNumeroComprobante(selectedIdComprobante)
            .then((numeroComprobante) => {
                // Actualizar el valor del campo numeroComprobanteRecibo con el número obtenido
                numeroComprobanteRecibo.value = numeroComprobante;
            })
            .catch((error) => {
                console.error("Error al obtener el número de comprobante:", error);
            });
    }

    // Función para obtener el número de comprobante basado en el ID del comprobante seleccionado
    function obtenerNumeroComprobante(idComprobante) {
        // Realizar una solicitud al servidor para obtener el número de comprobante
        return fetch('/recibo/obtenerNumeroComprobante?idComprobante=' + idComprobante)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('No se pudo obtener el número de comprobante');
                }
                return response.text();
            });
    }
});