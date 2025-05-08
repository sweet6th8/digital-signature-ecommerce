function updateQuantity(productId, inputField) {
    const quantity = parseInt(inputField.value, 10) || 1; // Default to 1 if invalid

    if (quantity <= 0) {
        alert("Quantity must be greater than zero.");
        inputField.value = 1; // Reset to 1 if invalid
        return;
    }

    fetch('cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=updateQuantity&productId=${encodeURIComponent(productId)}&quantity=${encodeURIComponent(quantity)}`,
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const itemTotalElement = document.querySelector(`#item-total-${productId}`);
                if (itemTotalElement) {
                    itemTotalElement.innerText = `${data.updatedItemTotal.toFixed(2)} `;
                }

                const totalPriceElement = document.getElementById("totalPrice");
                if (totalPriceElement) {
                    totalPriceElement.innerText = `${data.totalCartPrice.toFixed(2)} `;
                }

                displayCartMessage(data.message || "Quantity updated successfully.", false);
            } else {
                displayCartMessage(data.message || "Failed to update quantity.", true);
            }
        })
        .catch(error => {
            console.error("Error updating quantity:", error);
            displayCartMessage("An unexpected error occurred.", true);
        });
}

function removeItem(productId, rowElement) {
    if (!confirm("Are you sure you want to remove this item?")) {
        return;
    }

    fetch('cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=removeItem&productId=${encodeURIComponent(productId)}`,
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                rowElement.remove();

                const totalPriceElement = document.getElementById("totalPrice");
                if (totalPriceElement) {
                    totalPriceElement.innerText = `${data.totalCartPrice.toFixed(2)} `;
                }

                displayCartMessage(data.message || "Item removed successfully.", false);
            } else {
                displayCartMessage(data.message || "Failed to remove item.", true);
            }
        })
        .catch(error => {
            console.error("Error removing item:", error);
            displayCartMessage("An unexpected error occurred.", true);
        });
}


function displayCartMessage(message, isError) {
    const cartMessage = document.getElementById("cartMessage");
    cartMessage.className = isError ? 'alert alert-danger' : 'alert alert-success';
    cartMessage.innerText = message;
    cartMessage.style.display = 'block';

    setTimeout(() => {
        cartMessage.style.display = 'none';
    }, 3000);
}