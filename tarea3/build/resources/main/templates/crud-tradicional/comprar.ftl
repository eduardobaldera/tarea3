<#include "principal.ftl">
<head>
    <title>${titulo}</title>
</head>
<#macro page_body>
    <br>
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-4">${titulo}</h1>
            <br>
            <table class="table table-striped">

                <br>
                <thead>
                <tr>
                    <th scope="col" style="width:20%">ID</th>
                    <th scope="col" style="width:20%">Nombre</th>
                    <th scope="col" style="width:20%">Precio</th>
                    <th scope="col" style="width:20%">Cantidad</th>
                    <th scope="col" style="width:20%">Acciones</th>
                </tr>
                </thead>
                <tbody>
                    <#list lista as producto>
                        <tr>
                            <th scope="row">
                                <a href="/tarea2/visualizar/+${producto.id}">${producto.id}</a>
                            </th>
                            <td>${producto.nombre}</td>
                            <td>${producto.precio}</td>

                            <form method="post" id="form${producto.id}" action="/tarea2/agregar/${producto.id}">
                                <td>
                                    <input type="number" min="1" class="form-control" id="cantidad" name="cantidad" value="1">
                                </td>
                                <td>
                                    <button type="submit" form="form${producto.id}" class="btn btn-success">Agregar</button>
                                </td>
                            </form>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</#macro>
<@display_page/>

