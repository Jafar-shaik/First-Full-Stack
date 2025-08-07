function typeWriterEffect(text, elementId, speed = 40) {
  const element = document.getElementById(elementId);
  element.innerText = "";
  let i = 0;
  function type() {
    if (i < text.length) {
      element.innerText += text.charAt(i);
      i++;
      setTimeout(type, speed);
    }
  }
  type();
}

function fetchGreetingAndQuote() {
  const name = document.getElementById("nameInput").value || "Guest";
  const greetingElement = document.getElementById("greeting");
  const quoteElement = document.getElementById("quote");

  greetingElement.innerText = "Typing greeting... ✍️";
  quoteElement.innerText = "Loading quote... 💬";

  // Greeting API
  fetch(`http://127.0.0.1:8080/api/greeting?name=${encodeURIComponent(name)}`)
    .then((response) => {
      if (!response.ok) throw new Error("Greeting fetch failed");
      return response.json();
    })
    .then((data) => {
      typeWriterEffect(data.message, "greeting", 40);
    })
    .catch((error) => {
      console.error("Greeting Error:", error);
      typeWriterEffect("Something went wrong 😢", "greeting", 40);
    });

  // Quote API
  fetch("http://127.0.0.1:8080/api/quotes")
    .then((response) => {
      if (!response.ok) throw new Error("Quote fetch failed");
      return response.text();
    })
    .then((quote) => {
      typeWriterEffect(`💡 Quote: "${quote}"`, "quote", 35);
    })
    .catch((error) => {
      console.error("Quote Error:", error);
      quoteElement.innerText = "Failed to load quote 😞";
    });
}
