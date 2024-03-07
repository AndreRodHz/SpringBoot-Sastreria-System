// Define una función para buscar el cliente
function buscarCliente() {
    var numeroDocC = document.getElementById("numeroDocC").value;

    // Crea un formulario temporal
    var form = document.createElement("form");
    form.method = "post";
    form.action = "/venta/buscar_cliente"; // Cambia esto a la URL correcta

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