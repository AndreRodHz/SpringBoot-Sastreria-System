// Variables que necesitas antes de crear props
var idRecibo = document.getElementById('idRecibo').value;
var clienteRecibo = document.getElementById('clienteRecibo').value;
var fecha = document.getElementById('fecha').value;
var comprobante = document.getElementById('comprobante').value;
var baseRecibo = document.getElementById('baseRecibo').value;
var igv = document.getElementById('igv').value;
var totalRecibo = document.getElementById('totalRecibo').value;

var props = {
    outputType: jsPDFInvoiceTemplate.OutputType.Save,
    returnJsPDFDocObject: true,
    fileName: "Sastreria Guerrero",
    orientationLandscape: false,
    compress: true,
    logo: {
        src: "/imagenes/JG.png",
        type: 'PNG', //optional, when src= data:uri (nodejs case)
        width: 53.33, //aspect ratio = width/height
        height: 26.66,
        margin: {
            top: 0, //negative or positive num, from the current position
            left: 0 //negative or positive num, from the current position
        }
    },
    stamp: {
        inAllPages: true, //by default = false, just in the last page
        src: "https://raw.githubusercontent.com/edisonneza/jspdf-invoice-template/demo/images/qr_code.jpg",
        type: 'JPG', //optional, when src= data:uri (nodejs case)
        width: 20, //aspect ratio = width/height
        height: 20,
        margin: {
            top: 0, //negative or positive num, from the current position
            left: 0 //negative or positive num, from the current position
        }
    },
    business: {
        name: "Sastreria Guerrero",
        address: "Sector 2, Mz N, Lote 1, Grupo 7",
        phone: "Villa el Salvador - Lima",
        email: "(+51) 982 289 914",
        email_1: "R.U.C: 10463607309",
    },
    contact: {
        label: "Recibo generado para:",
        name: clienteRecibo,
        address: "Albania, Tirane, Astir",
        phone: "(+355) 069 22 22 222",
        email: "client@website.al",
        otherInfo: "www.website.al",
    },
    invoice: {
        label: comprobante.includes('B') ? 'Boleta: ' : 'Factura: ',
        num: comprobante,
        invDate: "Montos: " + baseRecibo +"  "+  igv +"  "+ totalRecibo,
        invGenDate: "Fecha de emición: " + fecha,
        headerBorder: false,
        tableBodyBorder: false,
        header: [
          {
            title: "#", 
            style: { 
              width: 10 
            } 
          }, 
          { 
            title: "ID",
            style: {
              width: 30
            } 
          }, 
          { 
            title: "Descripción",
            style: {
              width: 80
            } 
          }, 
          { title: "Precio"},
          { title: "Cantidad"},
          { title: "SubTotal"}
        ],
        table: Array.from(Array(10), (item, index)=>([
            index + 1,
            "There are many variations ",
            "Lorem Ipsum is simply dummy text dummy text ",
            200.5,
            4.5,
            400.5
        ])),
        additionalRows: [{
            col1: 'Total:',
            col2: '145,250.50',
            col3: 'ALL',
            style: {
                fontSize: 14 //optional, default 12
            }
        },
        {
            col1: 'VAT:',
            col2: '20',
            col3: '%',
            style: {
                fontSize: 10 //optional, default 12
            }
        },
        {
            col1: 'SubTotal:',
            col2: '116,199.90',
            col3: 'ALL',
            style: {
                fontSize: 10 //optional, default 12
            }
        }],
        invDescLabel: "",
        invDesc: "",
    },
    footer: {
        text: "The invoice is created on a computer and is valid without the signature and stamp.",
    },
    pageEnable: true,
    pageLabel: "Page ",
};


// Función para generar el PDF con los detalles y mostrar el objeto creado en la consola
function generarPDF() {
    var pdfObject = jsPDFInvoiceTemplate.default(props);
    console.log("Object created: ", pdfObject);
}
