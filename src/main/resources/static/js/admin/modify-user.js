var entreprise = null;
const oldEmail = document.getElementById('email').value;
function filterFunction() {
    var input, filter, select, options, option, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    select = document.getElementById("selectNames");
    options = select.getElementsByTagName("option");
    for (i = 0; i < options.length; i++) {
        option = options[i];
        if (option.textContent.toUpperCase().indexOf(filter) > -1) {
            option.style.display = "";
        } else {
            option.style.display = "none";
        }
    }
}

function updateSearchBar(selectObject) {
    var selectedName = selectObject.value;
    let nomMatch = selectedName.match(/nom=([^,\)]+)/);
    let idMatch = selectedName.match(/id=([^,\)]+)/);
    if (nomMatch && nomMatch.length > 1) {
        let nomValue = nomMatch[1];
        document.getElementById('myInput').value = nomValue;
        document.getElementById("myDropdown").classList.remove("show");
    }

    if(idMatch && idMatch.length > 1){
        let idValue = idMatch[1];
        entreprise = idValue;
    }
}


async function modifyUser(type, id){
    const nom = document.getElementById('nom');
    const prenom = document.getElementById('prenom');
    const email = document.getElementById('email');
    const promo = document.getElementById('promo');
    const matricule = document.getElementById('matricule');
    const filliere = document.getElementById('filliere');
    const niveau = document.getElementById('niveau');
    const post = document.getElementById('post');


    // using this field (newMail) to send the mail with the body only if changed by the user
    // else send null
    const newMail = (oldEmail == email.value) ? null : email.value;
    const data = {
        nom: nom ? nom.value : null,
        prenom: prenom ? prenom.value : null,
        email: newMail,
        mdp: mdp ? mdp.value : null,
        promo: promo ? promo.value : null,
        matricule: matricule ? matricule.value : null,
        filliere: filliere ? filliere.value : null,
        niveau: niveau && niveau.value ? parseInt(niveau.value) : null,
        post: post ? post.value : null,
        entreprise : entreprise != null ? {id: entreprise} : null
    }
    const req = await fetch(`/admin/modify-user/${type}/${id}`,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    const res = await req;
    if( res.ok){
        console.log(res.json());
        location.reload();
    }else{
        alert('error');
    }

}

document.getElementById('modify-user').addEventListener('click', (e)=>{
    e.preventDefault();
    const url = window.location.href.split('/');
    const type = url[url.length - 2];
    const id = url[url.length - 1];
    modifyUser(type,parseInt(id));

});