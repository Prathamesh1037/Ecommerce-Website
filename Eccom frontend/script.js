  const payButton = document.querySelector(".pay-button");
  payButton.addEventListener("click", function () {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const couponInput = document.getElementById("coupon"); // If you have a coupon input

    // Reset validation
    document.querySelectorAll(".form-group").forEach((group) => {
      group.classList.remove("error");
    });

    let hasError = false;
    if (!name) {
      document.getElementById("name").parentElement.classList.add("error");
      hasError = true;
    }
    if (!email || !isValidEmail(email)) {
      document.getElementById("email").parentElement.classList.add("error");
      hasError = true;
    }
    if (!phone || !isValidPhone(phone)) {
      document
        .getElementById("phone")
        .parentElement.parentElement.classList.add("error");
      hasError = true;
    }

    if (hasError) {
      shakeButton();
      alert("Please fill all required fields correctly.");
      return;
    }

    payButton.innerHTML =
      '<i class="fas fa-spinner fa-spin"></i> Processing...';
    payButton.disabled = true;

    // Create Razorpay order via backend
    fetch("http://localhost:8080/api/payment/create-order", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: name,
        email: email,
        phone: phone,
        amount: currentPrice,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        const razorpayOrderId = data.id;

        const options = {
          key: "rzp_test_RekcF1I5UUtAeF",
          amount: currentPrice * 100,
          currency: "INR",
          name: "Spark 3.0 by Genie Ashwani",
          description: "Java Full Stack Course Enrollment",
          order_id: razorpayOrderId,
          theme: {
            color: "#4f46e5", // Match your button color
          },
          prefill: {
            name: name,
            email: email,
            contact: phone,
          },
          modal: {
            backdropclose: false,
            escape: false,
            ondismiss: function () {
              payButton.innerHTML = "Pay ₹" + currentPrice;
              payButton.disabled = false;
            },
          },
          handler: function (response) {
            // On success: update backend
            fetch("http://localhost:8080/api/payment/update-order", {
              method: "POST",
              headers: {
                "Content-Type": "application/x-www-form-urlencoded",
              },
              body: new URLSearchParams({
                paymentId: response.razorpay_payment_id,
                orderId: response.razorpay_order_id,
                status: "SUCCESS",
              }),
            })
              .then(() => {
                payButton.innerHTML =
                  '<i class="fas fa-check"></i> Payment Successful!';
                payButton.style.background =
                  "linear-gradient(to right, #10b981, #059669)";
                alert(
                "\nYour payment of ₹${currentPrice} was successful. A confirmation email has been sent.`"
                );
              })
              .catch(() => {
                alert("Payment succeeded but failed to update backend.");
              });
          },
          theme: {
            color: "#0f766e",
          },
        };

        const rzp = new Razorpay(options);
        rzp.open();
      })
      .catch((err) => {
        console.error("Error:", err);
        alert("Something went wrong. Please try again.");
        payButton.disabled = false;
        payButton.innerHTML = "Pay Now";
      });
  });

  function isValidEmail(email) {
    return /\S+@\S+\.\S+/.test(email);
  }

  function isValidPhone(phone) {
    return /^\d{10}$/.test(phone);
  }

  function shakeButton() {
    payButton.classList.add("shake");
    setTimeout(() => payButton.classList.remove("shake"), 500);
  }

  // Helper functions
  function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }

  function isValidPhone(phone) {
    return /^\d{10}$/.test(phone);
  }

  function shakeButton() {
    payButton.classList.add("shake");
    setTimeout(() => {
      payButton.classList.remove("shake");
    }, 500);
  }

  // Add CSS for validation and animations
  const style = document.createElement("style");
  style.textContent = `
      .form-group.error input {
          border-color: #ef4444;
          background-color: #fef2f2;
      }
      
      .form-group.focused input {
          border-color: #4f46e5;
          box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
      }
      
      .shake {
          animation: shake 0.5s cubic-bezier(.36,.07,.19,.97) both;
      }
      
      @keyframes shake {
          10%, 90% { transform: translate3d(-1px, 0, 0); }
          20%, 80% { transform: translate3d(2px, 0, 0); }
          30%, 50%, 70% { transform: translate3d(-4px, 0, 0); }
          40%, 60% { transform: translate3d(4px, 0, 0); }
      }
      
      .price-row span:last-child {
          transition: all 0.3s ease;
      }
  `;
  document.head.appendChild(style);

