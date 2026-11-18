<div>
    <h1>Bem-vindo à Estante Web</h1>
    <#if usuarioLogado??>
        <p>
            Olá, ${usuarioLogado.nome}
            <form method="post" action="/logout" style="display:inline;">
                <button type="submit">Sair</button>
            </form>
        </p>
    <#else>
        <p><a href="/login">Entrar</a></p>
    </#if>
</div>