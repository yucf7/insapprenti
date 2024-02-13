function filterFunctionInsa() {
    var input, filter, select, options, option, i;
    input = document.getElementById("myInputInsa");
    filter = input.value.toUpperCase();
    select = document.getElementById("selectNamesInsa");
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

function updateSearchBarInsa(selectObject, id) {
    setTuteur(id, selectObject.value, 0);
}

function updateSearchBarEntreprise(selectObject, id) {
    setTuteur(id, selectObject.value, 1);
}


function filterFunctionEntreprise() {
    var input, filter, select, options, option, i;
    input = document.getElementById("myInputEntreprise");
    filter = input.value.toUpperCase();
    select = document.getElementById("selectNamesEntreprise");
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


async function setTuteur(apprentiId, id, type){
    const res = await fetch(
        `/admin/setTuteur/${apprentiId}`,
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                tuteurInsa : (type == 0 ? parseInt(id) : null),
                tuteurEntreprise : (type == 1 ? parseInt(id) : null)
            })
        }

    );
    const data = await res.json();
    if(res.ok){
        location.reload();
    }
    else{
        alert("Une erreur est survenue !")
    }
}

async function removeTuteurAssign(userId, type){
    const res = await fetch(
        `/admin/removeTuteur/${userId}/${type}`,
        {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        }
    );
    const data = await res.json();

    if(data.success || res.ok){
        location.reload();
    }
    else{
        alert("Une erreur est survenue !")
    }
}
if(document.getElementById('tuteur0'))
document.getElementById('tuteur0').addEventListener('click', (e) =>{
    e.preventDefault();
    const url = window.location.href.split('/');
    const id = url[url.length - 1];
    removeTuteurAssign(id, 0);
});
if(document.getElementById('tuteur1'))
document.getElementById('tuteur1').addEventListener('click', (e) =>{
    e.preventDefault();
    const url = window.location.href.split('/');
    const id = url[url.length - 1];
    removeTuteurAssign(id, 1);
});
