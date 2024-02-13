var entreprise = null;
const errorField = document.getElementById('error-field');
const createUser = document.getElementById('create-user');

const nom = document.getElementById('nom');
const prenom = document.getElementById('prenom');
const post = document.getElementById('post');

var entreprise = null;
const mdp = document.getElementById('mdp');
const email = document.getElementById('email');


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

createUser.addEventListener('click', async (e)=>{
    e.preventDefault();
    errorField.innerHTML = "";
    const data = {
        nom : nom.value,
        prenom : prenom.value,
        post : post.value,
        entreprise : {id: entreprise},
        mdp : mdp.value,
        email : email.value,
        role : 4
    }

    const isEmptyField = Object.values(data).find(value => value === '' || value === null || value === undefined) !== undefined;
    if(isEmptyField){
        errorField.innerHTML = "Vous devez fournir tous les champs";
    }else{
        try{
            const res = await fetch('/admin/add-user',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            if (res.status === 409) { // when there is a conflict with other email address
                errorField.innerHTML = `L'adresse mail ${email.value} existe déjà. <a href="/admin/modify-user">Modifier l'utilisateur ?</a>`
            } else if (res.ok) {
                const response = await res.json();
                alert(`Utilisateur ${response.nom} créé. Redirection dans 1 seconde...`);
                setTimeout(() => {
                    location.assign('/admin');
                }, 1000);

            } else {
                console.error('Unexpected error:', res.status);
            }
        } catch (error) {
            console.error('Fetch error:', error);
        }
    }


});


// function to generate 'mot de passe' from alphabet (upper and lower) case
function generateMdp(e){
    e.preventDefault();
    const mdp_field = document.getElementById('mdp');
    const lowerAlpha = Array.from(Array(26)).map((e, i) => i + 97);
    const lowerAlphabet = lowerAlpha.map((x) => String.fromCharCode(x));
    const upperAlpha = Array.from(Array(26)).map((e, i) => i + 65);
    const uppserAlphabet = upperAlpha.map((x) => String.fromCharCode(x));

    var result = '';
    for (let _=0; _<=3 ; _++){
        const randomNumber1 = Math.floor(Math.random() * uppserAlphabet.length);
        const randomNumber2 = Math.floor(Math.random() * uppserAlphabet.length);
        result += lowerAlphabet[randomNumber1] + uppserAlphabet[randomNumber2];
    }
    mdp_field.value = result;
}

// clear error-field on email input
email.addEventListener('change', ()=> {
    errorField.innerHTML = ""
});
