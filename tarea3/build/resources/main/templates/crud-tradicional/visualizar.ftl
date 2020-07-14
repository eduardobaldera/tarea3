<#include "principal.ftl">
<!DOCTYPE html>
<head>
    <title>${titulo}</title>
</head>

<#macro page_body>
    <div class="container">
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-4">${titulo}</h1>
        </div>

        <!-- El endpoint que estará procesando el formulario será enviado por el controlador      -->
        <form enctype="application/x-www-form-urlencoded" method="post" action=tarea2/visualizar >
            <div class="form-group">
                <label for="matriculaForm">ID</label>
                <!-- La variable visualizar aplica el formulario readonly.                -->
                <input readonly value="${producto.id}" type="number" name="matricula" class="form-control" id="matriculaForm" aria-describedby="matriculaHelp">
                <small id="matriculaHelp" class="form-text text-muted">Indicar el ID</small>
            </div>
            <div class="form-group">
                <label for="nombreForm">Nombre</label>
                <input readonly value="${producto.nombre}" type="text"  name="nombre" class="form-control" id="nombreForm">
            </div>
            <div class="form-group">
                <label for="carreraForm">Precio</label>
                <input readonly value="${producto.precio}" type="text"  name="carrera" class="form-control" id="carreraForm">
            </div>
            <!-- Los botones para la creación del producto -->
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="/tarea2/" class="btn btn-primary">Cancelar</a>
        </form>
    </div>
</#macro>

<@display_page/>