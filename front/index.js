const apiGif = "http://localhost:8080/api/v1/gifs/random"
const apiCurrencies = "http://localhost:8080/api/v1/currencies/"

const btn = document.getElementById("btn")
const img = document.getElementById("img")
const rate_was = document.getElementById("rate_was")
const rate_now = document.getElementById("rate_now")
const img_container = document.getElementById("img_container")

const getImg = async (id) => {
	symbol = document.getElementById('searchTag').value
    const rateYesterday = await (await fetch(apiCurrencies + symbol + "/latest", {method: 'GET'})).json()
	const rateLatest = await (await fetch(apiCurrencies + symbol + "/yesterday", {method: 'GET'})).json()
	rate_was.textContent = rateYesterday
	rate_now.textContent = rateLatest
	tag = rateLatest > rateYesterday ? "rich": "broke";
	if (tag == "rich") {
		img_container.style.backgroundColor = "rgb(106, 214, 183)"
	} else {
		img_container.style.backgroundColor = "rgb(239, 47, 37)"
	}
	const gif = await (await fetch(apiGif + "?tag=" + tag, {method: 'GET'})).json()
    img.src = gif['url']
}

btn.addEventListener("click", () =>{
    getImg()
})

