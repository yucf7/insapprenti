
const errorField = document.getElementById('error-field2');
const createUser = document.getElementById('create-user');

const nom = document.getElementById('nom2');
const prenom = document.getElementById('prenom2');
const post = document.getElementById('post');


const mdp = document.getElementById('mdp2');
const email = document.getElementById('email2');




createUser.addEventListener('click', async (e)=>{
    e.preventDefault();
    const entreprise2 = document.getElementById("selectNames");
    if(!entreprise2 || !entreprise2.value){
        alert("Veuillez séléctionner l'entreprise avant d'ajouter un tuteur");
        return;
    }
    errorField.innerHTML = "";
    const data = {
        nom : nom.value,
        prenom : prenom.value,
        post : post.value,
        entreprise : {id: entreprise2.value.toString().substring(14,15)},
        mdp : mdp.value,
        email : email.value,
        role : 4
    }

    const isEmptyField = Object.values(data).find(value => value === '' || value === null || value === undefined) !== undefined;
    if(isEmptyField){
        errorField.innerHTML = "Vous devez fournir tous les champs";
    }else{
        try{
            const url = window.location.href.split('/');
            const id = url[url.length - 1];
            const res = await fetch(`/admin/add-user?dialog=${id}`,{
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
                    location.reload();
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
function generateMdp2(e){
    e.preventDefault();
    const mdp_field = document.getElementById('mdp2');
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

