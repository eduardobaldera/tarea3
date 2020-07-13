<#macro page_head>
    <title>No title in page_head</title>
</#macro>

<#macro page_body>
    <h1>No page_body</h1>
</#macro>

<#macro display_page>
    <!doctype html>
    <!-- Creando el fragmento en la plantilla-->
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <title>Plantilla Crud Tradicional</title>
        <@page_head/>
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample08" aria-controls="navbarsExample08" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-md-center" id="navbarsExample08" >
            <ul class="navbar-nav">

                <li class="nav-item active">
                    <a class="navbar-brand" href="/tarea2/comprar">TAREA 2 </a>
                </li>
                 <#if admin == true>
                <li class="nav-item">
                    <a class="nav-link" href="/tarea2/comprar">Comprar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tarea2/listar">Administrar Productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="carrito">Carrito</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tarea2/ventas">Ventas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout: ${usuario.nombre}</a>
                </li>
                <#elseif usr == true>
                 <li class="nav-item">
                    <a class="nav-link" href="/tarea2/comprar">Comprar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="carrito">Carrito</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout: ${usuario.nombre}</a>
                </li>
                <#else>
                <li class="nav-item">
                    <a class="nav-link" href="/tarea2/comprar">Comprar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="carrito">Carrito</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login">Login</a>
                </li>
                </#if>

            </ul>
        </div>
    </nav>


    <@page_body/>
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>