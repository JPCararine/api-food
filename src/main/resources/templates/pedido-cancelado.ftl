<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pedido Cancelado</title>
</head>

<body style="margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f4f4;">

<table width="100%" cellpadding="0" cellspacing="0" style="padding:20px;">
    <tr>
        <td align="center">

            <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:8px; padding:20px;">


                <tr>
                    <td style="text-align:center;">
                        <h2 style="color:#e74c3c; margin-bottom:10px;">
                            Pedido Cancelado ❌
                        </h2>
                    </td>
                </tr>


                <tr>
                    <td style="font-size:14px; color:#333;">
                        <p>
                            Olá, <strong>${pedido.usuario.nome!"Cliente"}</strong>!
                        </p>

                        <p>
                            Seu pedido no restaurante
                            <strong>${pedido.restaurante.nome!"Restaurante"}</strong>
                            foi cancelado.
                        </p>

                        <p>
                            <strong>Código do pedido:</strong> ${pedido.codigo}
                        </p>
                    </td>
                </tr>


                <tr>
                    <td style="padding-top:20px;">
                        <h3 style="border-bottom:1px solid #ddd; padding-bottom:5px;">
                            Itens do pedido
                        </h3>

                        <table width="100%" cellpadding="8" cellspacing="0" style="font-size:14px; border-collapse:collapse;">
                            <tr style="background:#f8f8f8;">
                                <th align="left">Produto</th>
                                <th align="center">Qtd</th>
                                <th align="right">Preço</th>
                            </tr>

                            <#list pedido.itens as item>
                                <tr style="border-bottom:1px solid #eee;">
                                    <td>
                                        ${item.produto.nome!"Produto indisponível"}
                                    </td>
                                    <td align="center">
                                        ${item.quantidade}
                                    </td>
                                    <td align="right">
                                        R$ ${item.precoUnitario?string["0.00"]}
                                    </td>
                                </tr>
                            </#list>
                        </table>
                    </td>
                </tr>


                <tr>
                    <td style="padding-top:20px; text-align:right;">
                        <strong>
                            Total: R$ ${pedido.valorTotal?string["0.00"]}
                        </strong>
                    </td>
                </tr>


                <tr>
                    <td style="padding-top:30px; font-size:12px; color:#888; text-align:center;">
                        <p>
                            Se tiver dúvidas, entre em contato com o restaurante.
                        </p>
                    </td>
                </tr>

            </table>

        </td>
    </tr>
</table>

</body>
</html>