<#include "cabecalho.ftlh">

<h2>Entrar</h2>

<#if erro??>
    <p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/login">
    <p>
        <label>E-mail:
            <input type="email" name="email" value="${email!}">
        </label>
    </p>
    <p>
        <label>Senha:
            <input type="password" name="senha">
        </label>
    </p>
    <button type="submit">Entrar</button>
</form>
