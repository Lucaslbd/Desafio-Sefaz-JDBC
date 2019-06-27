/* global PF */

function verificar2(xhr, status, args, dlg) {
    if (args.validationFailed) {
        PF(dlg).jq.effect("shake", {times: 5}, 100);
    } else {
        PF(dlg).hide();
    }
}

function abrirzap(telefone) {
    ddd = telefone.substring(1, 3) + "9";
    num1 = telefone.substring(7, 11);
    num2 = telefone.substring(12, 16);
    zap = ddd + num1 + num2;
    window.open("http://api.whatsapp.com/send?1=pt_BR&phone=55" + zap, '_blank');
}


















