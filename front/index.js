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
		img_container.style.backgroundColor = "rgb(137, 255, 204)"
	} else {
		img_container.style.backgroundColor = "rgb(251, 205, 205)"
	}
	const gif = await (await fetch(apiGif + "?tag=" + tag, {method: 'GET'})).json()
    img.src = gif['url']
}

btn.addEventListener("click", () =>{
    getImg()
})

// try {
// 	const response = await fetch("http://localhost:8000/api/v1/book", {
// 	  headers: {
// 		Accept: "application/json"
// 	  }
// 	});
// 	if (!response.ok) {
// 	  if (response.status === 429) {
// 		// displaying "wow, slow down mate"
// 	  } else if (response.status === 403) {
// 		// displaying "hm, what about no?"
// 	  } else {
// 		// displaying "dunno what happened \_(ツ)_/¯"   
// 	  }
// 	  throw new Error(response);
// 	}
// 	const books = await response.json();
  
// 	// storing my books
//   } catch(exception) {
// 	// storing my error onto Sentry
//   }