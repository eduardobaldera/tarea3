<#include "principal.ftl">

<head>
    <title>${titulo}</title>
</head>
<#macro page_body>
    <br>
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-4">${titulo}.</h1>
            <br>

            <#if admin == true>
            <#list ventas as venta>
                <h5 style="margin-left: 10rem">${venta.nombreCliente} ${venta.fechaCompra?date}</h5>
                <table class="table table-striped">
                    <br>
                    <thead>
                    <tr>
                        <th scope="col" style="width:25%">ID</th>
                        <th scope="col " style="width:25%">Nombre</th>
                        <th scope="col" style="width:25%">Precio</th>
                        <th scope="col" style="width:25%">Cantidad </th>
                        <th scope="col" style="width:25%">Total </th>
                    </tr>
                    </thead>
                    <tbody>
                    <#assign total_venta = 0>
                        <#list venta.listaProductos as producto>
                            <#assign total = producto.cantidad * producto.precio>
                            <#assign total_venta += total>
                            <tr>
                                <th scope="row">
                                    <a href="visualizar/+${producto.id}"/>${producto.id}</a>
                                </th>
                                <td>${producto.nombre}</td>
                                <td>${producto.precio}</td>
                                <td>${producto.cantidad}</td>
                                <td>${total}</td>
                            </tr>
                        </#list>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2" class="hidden-xs"></td>
                        <td class="hidden-xs text-center"><strong>Total ${total_venta}</strong></td>
                    </tr>
                    </tfoot>
                </table>
                <br>
            </#list>
            <#else>
                <h1 class="display-4">Solo para Administrador</h1>
            </#if>
        </div>
    </div>
</#macro>

<@display_page/>