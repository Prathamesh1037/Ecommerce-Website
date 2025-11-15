let cart = JSON.parse(localStorage.getItem("cart")) || [];

function loadCart() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let cartItems = document.getElementById("cart-items");
  let totalElement = document.getElementById("total-amount");

  // ✅ If not on cart.html, skip rendering
  if (!cartItems || !totalElement) {
    updateCartCounter(); // still update navbar cart count
    return;
  }

  let totalAmount = 0;
  cartItems.innerHTML = "";

  cart.forEach((item, index) => {
    let itemTotal = item.price * item.quantity;
    totalAmount += itemTotal;

    cartItems.innerHTML += `
      <tr>
        <td><img src="${item.imageUrl}" width="50"></td>
        <td>${item.name}</td>
        <td>${item.price}</td>
        <td>
          <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, -1)">-</button>
          ${item.quantity}
          <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, 1)">+</button>
        </td>
        <td>₹ ${itemTotal}</td>
        <td><button class="btn btn-danger btn-sm" onclick="removeFromCart(${index})">X</button></td>
      </tr>
    `;
  });

  totalElement.innerText = totalAmount;
  updateCartCounter(); // ✅ refresh cart badge after re-render
}

function addToCart(id, name, price, imageUrl) {
  console.log("Adding product to cart:", id, name, price, imageUrl);
  price = parseFloat(price);
  let cart = JSON.parse(localStorage.getItem("cart")) || [];

  let itemIndex = cart.findIndex((item) => item.id === id);

  if (itemIndex !== -1) {
    cart[itemIndex].quantity += 1;
  } else {
    cart.push({ id, name, price, imageUrl, quantity: 1 });
  }

  localStorage.setItem("cart", JSON.stringify(cart));
  updateCartCounter();
}

function updateCartCounter() {
  // ✅ Always read the current cart from localStorage
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  const badge = document.querySelector(".cart-badge");
  if (badge) badge.innerText = cart.length;
}

function changeQuantity(index, change) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart[index].quantity += change;
  if (cart[index].quantity <= 0) cart.splice(index, 1);
  localStorage.setItem("cart", JSON.stringify(cart));
  loadCart();
}

function removeFromCart(index) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart.splice(index, 1);
  localStorage.setItem("cart", JSON.stringify(cart));
  loadCart();
}

// ✅ Ensure loadCart runs when the DOM is ready
document.addEventListener("DOMContentLoaded", loadCart);
// At top of cart.js ensure BASE_URL if needed or imported
// store cart state in sessionStorage and redirect to payment page
function checkout() {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  if (!cart.length) {
    alert("Your cart is empty.");
    return;
  }

  // compute total
  const totalAmount = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  // Save checkout data in sessionStorage for the payment page to use
  sessionStorage.setItem(
    "checkoutData",
    JSON.stringify({
      cart,
      totalAmount,
      currency: "INR",
    })
  );

  // redirect to payment page
  window.location.href = "payment.html";
}
