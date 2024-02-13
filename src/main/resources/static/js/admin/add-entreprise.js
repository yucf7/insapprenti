const nom = document.getElementById('nom');
const siege = document.getElementById('siege');
const adresse = document.getElementById('adresse');
const submit = document.getElementById('create-entreprise');

submit.addEventListener('click', async () => {
    try {
        const res = await fetch('/admin/add-entreprise', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nom: nom.value,
                siege: siege.value,
                adresse: adresse.value
            })
        });

        if (!res.ok) {
            throw new Error('Network response was not ok.');
        }

        const data = await res.json();
        if(data.id) {
            alert(`Entreprise ${data.nom} créée avec succes. Redirection...`)
            location.reload();
        }
        else alert('error')
    } catch (error) {
        console.error('Error:', error);
    }
});