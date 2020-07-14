
<#include "principal.ftl">
<#macro page_body>
<br>
<div class="container">
    <div class="jumbotron">
       <h1 class="display-4">${titulo}</h1>
        <br>

        <a href="/tarea2/crear" class="btn btn-primary">Nuevo Producto</a>
        <br>
        <table class="table table-striped">
            <br>
            <thead>
            <tr>
                <th scope="col" style="width:25%">ID</th>
                <th scope="col" style="width:25%">Nombre</th>
                <th scope="col" style="width:25%">Precio</th>
                <th scope="col" style="width:25%">Acciones</th>
            </tr>
            </thead>
            <tbody>
                <#list lista as prod>
                <tr>
                    <td>${prod.id}</td>
                    <td>${prod.nombre}</td>
                    <td>${prod.precio}</td>
                    <td>
                        <a class="btn btn-secondary btn-sm" href="/tarea2/editar/${prod.id}">Editar</a>
                        <a class="btn btn-danger btn-sm" href="/tarea2/eliminar/${prod.id}">Eliminar</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#macro>
<@display_page/>

