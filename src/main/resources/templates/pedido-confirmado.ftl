<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>

<body style="margin:0; padding:0; font-family: Arial, Helvetica, sans-serif; font-size:14px;">

<table width="100%" cellpadding="0" cellspacing="0" style="max-width:600px; margin:auto;">


    <tr>
        <td style="padding:20px 0;">
            <h1 style="color:red; font-size:26px; margin:0;">
                Pedido confirmado!
            </h1>
        </td>
    </tr>


    <tr>
        <td style="padding-bottom:20px;">
            <p style="margin:0;">
                ${pedido.usuario.nome}, seu pedido foi confirmado pelo restaurante e já está sendo preparado.
            </p>
        </td>
    </tr>


    <tr>
        <td>
            <h2 style="font-size:20px; margin-bottom:10px;">
                ${pedido.restaurante.nome}
            </h2>
        </td>
    </tr>


    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" style="color:#6F6F6F;">

                <#list pedido.itens as item>
                <tr>
                    <td style="padding:8px 0;">
                        ${item.quantidade}x ${item.produto.nome}
                    </td>
                    <td style="text-align:right;">
                        ${item.precoTotal?string.currency}
                    </td>
                </tr>
            </#list>

    <tr>
        <td style="padding:8px 0;">Frete</td>
        <td style="text-align:right;">
            ${pedido.taxaFrete?string.currency}
        </td>
    </tr>

    <tr style="font-weight:bold;">
        <td style="padding:10px 0;">Total</td>
        <td style="text-align:right;">
            ${pedido.valorTotal?string.currency}
        </td>
    </tr>

</table>
</td>
</tr>


<tr>
    <td style="padding-top:20px;">
        <h2 style="font-size:20px; margin-bottom:5px;">
            Forma de pagamento
        </h2>
        <p style="color:#6F6F6F; margin:0;">
            ${pedido.formaPagamento.descricao}
        </p>
    </td>
</tr>

</table>

</body>
</html>