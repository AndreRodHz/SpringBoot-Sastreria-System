// Define una función para buscar el cliente
function buscarCliente() {
    var numeroDocC = document.getElementById("numeroDocC").value;

    // Crea un formulario temporal
    var form = document.createElement("form");
    form.method = "post";
    form.action = "/pedido/buscar_cliente"; // Cambia esto a la URL correcta

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

document.getElementById("mostrarMedidasSwitchS").addEventListener("click", function () {
    var medidasFormS = document.getElementById("medidasFormS"); // Usar el ID correcto
    var mostrarMedidasSwitchS = document.getElementById("mostrarMedidasSwitchS"); // Usar el ID correcto

    if (mostrarMedidasSwitchS.checked) {
        medidasFormS.style.display = "block"; // Si está encendido, muestra el formulario
        habilitarCamposMedidasSuperiores(); // Llama a la función para habilitar los campos de medidas superiores
    } else {
        medidasFormS.style.display = "none"; // Si está apagado, oculta el formulario
        deshabilitarCamposMedidasSuperiores(); // Llama a la función para deshabilitar los campos de medidas superiores
    }
});

// Añade este código para ocultar el formulario de medidas superiores al principio
document.addEventListener("DOMContentLoaded", function () {
    var medidasFormS = document.getElementById("medidasFormS"); // Usar el ID correcto
    var mostrarMedidasSwitchS = document.getElementById("mostrarMedidasSwitchS"); // Usar el ID correcto

    if (!mostrarMedidasSwitchS.checked) {
        medidasFormS.style.display = "none"; // Oculta el formulario de medidas superiores al cargar la página si el interruptor está apagado
        deshabilitarCamposMedidasSuperiores(); // Llama a la función para deshabilitar los campos de medidas superiores
    }
});

document.getElementById("mostrarMedidasSwitchI").addEventListener("click", function () {
    var medidasFormI = document.getElementById("medidasFormI"); // Usar el ID correcto
    var mostrarMedidasSwitchI = document.getElementById("mostrarMedidasSwitchI"); // Usar el ID correcto

    if (mostrarMedidasSwitchI.checked) {
        medidasFormI.style.display = "block"; // Si está encendido, muestra el formulario de medidas inferiores
        habilitarCamposMedidasInferiores(); // Llama a la función para habilitar los campos de medidas inferiores
    } else {
        medidasFormI.style.display = "none"; // Si está apagado, oculta el formulario de medidas inferiores
        deshabilitarCamposMedidasInferiores(); // Llama a la función para deshabilitar los campos de medidas inferiores
    }
});

// Añade este código para ocultar el formulario de medidas inferiores al principio
document.addEventListener("DOMContentLoaded", function () {
    var medidasFormI = document.getElementById("medidasFormI"); // Usar el ID correcto
    var mostrarMedidasSwitchI = document.getElementById("mostrarMedidasSwitchI"); // Usar el ID correcto

    if (!mostrarMedidasSwitchI.checked) {
        medidasFormI.style.display = "none"; // Oculta el formulario de medidas inferiores al cargar la página si el interruptor está apagado
        deshabilitarCamposMedidasInferiores(); // Llama a la función para deshabilitar los campos de medidas inferiores
    }
});

var textarea = document.getElementById("descripcionPedido");
var contadorCaracteres = document.getElementById("contadorCaracteres");

textarea.addEventListener("input", function () {
    var caracteresRestantes = 255 - textarea.value.length;
    contadorCaracteres.textContent = caracteresRestantes;
});

// Función para habilitar los campos de medidas superiores
function habilitarCamposMedidasSuperiores() {
    var camposMedidasSuperiores = document.querySelectorAll("#medidasFormS input");
    camposMedidasSuperiores.forEach(function (campo) {
        campo.required = true;
    });
}

// Función para deshabilitar los campos de medidas superiores
function deshabilitarCamposMedidasSuperiores() {
    var camposMedidasSuperiores = document.querySelectorAll("#medidasFormS input");
    camposMedidasSuperiores.forEach(function (campo) {
        campo.required = false;
    });
}

// Función para habilitar los campos de medidas inferiores
function habilitarCamposMedidasInferiores() {
    var camposMedidasInferiores = document.querySelectorAll("#medidasFormI input");
    camposMedidasInferiores.forEach(function (campo) {
        campo.required = true;
    });
}

// Función para deshabilitar los campos de medidas inferiores
function deshabilitarCamposMedidasInferiores() {
    var camposMedidasInferiores = document.querySelectorAll("#medidasFormI input");
    camposMedidasInferiores.forEach(function (campo) {
        campo.required = false;
    });
}

// Función para validar que al menos un checkbox esté activado
function validarCheckbox() {
	var idCliente = document.getElementById("idCliente").value;
    var mostrarMedidasSwitchS = document.getElementById("mostrarMedidasSwitchS");
    var mostrarMedidasSwitchI = document.getElementById("mostrarMedidasSwitchI");
    var tablaCarrito = document.querySelector('.table tbody'); // Obtener el tbody de la tabla del carrito
    var filasCarrito = tablaCarrito.getElementsByTagName('tr'); // Obtener todas las filas dentro del tbody
    
	if (idCliente === '') { // Verificar si el valor es una cadena vacía
        alert("No se puede generar Pedido sin Cliente");
        return false; // Evita que se envíe el formulario
    }

    if (!mostrarMedidasSwitchS.checked && !mostrarMedidasSwitchI.checked) {
        alert("Debes ingresar las medidas correspondientes");
        return false; // Evita que se envíe el formulario
    }
    
    if (filasCarrito.length === 0) {
        alert("El carrito está vacío. Agrega productos para continuar.");
        return false; // Evita que se continúe con la acción
    }
    

    return true; // Permite enviar el formulario si al menos un checkbox está activado
}

// Agrega un evento al formulario para llamar a la función de validación antes de enviar
document.querySelector("form").addEventListener("submit", function (event) {
    if (!validarCheckbox()) {
        event.preventDefault(); // Evita que se envíe el formulario si no se cumplen las condiciones
    }
});
