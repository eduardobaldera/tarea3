<#include "principal.ftl">
<!DOCTYPE html>
<head>
    <title>${titulo}</title>
</head>

<#macro page_body>
    <#if admin == true>
        <div class="container">
            <div class="jumbotron">
                <h1 class="display-4">${titulo}</h1>
            </div>
            <!-- El endpoint que estará procesando el formulario será enviado por el controlador      -->
            <form enctype="application/x-www-form-urlencoded" method="post" action=${accion} >
                <div class="form-group">
                    <label for="matriculaForm">ID</label>
                    <!-- La variable visualizar aplica el formulario readonly.                -->
                    <input value="<#if producto??> ${producto.id} <#else></#if>" type="number" name="matricula" class="form-control" id="matriculaForm" aria-describedby="matriculaHelp">
                    <small id="matriculaHelp" class="form-text text-muted">Indicar el ID</small>
                </div>
                <div class="form-group">
                    <label for="nombreForm">Nombre</label>
                    <input value="<#if producto??> ${producto.nombre} <#else></#if>" type="text"  name="nombre" class="form-control" id="nombreForm">
                </div>
                <div class="form-group">
                    <label for="carreraForm">Precio</label>
                    <input value="<#if producto??> ${producto.precio} <#else></#if>" type="text"  name="carrera" class="form-control" id="carreraForm">
                </div>
                <!-- Los botones para la creación del producto -->
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="/tarea2/" class="btn btn-primary">Cancelar</a>
            </form>
        </div>
    <#else>
        <h1 class="display-4">Solo para Administrador</h1>
    </#if>
</#macro>

<@display_page/>