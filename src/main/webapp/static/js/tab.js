console.log("check event")
const tab = document.getElementsByClassName("tab-content");
const tabLink = document.getElementsByClassName("tab-btn");
const listLink = Array.from(tabLink);
const listTab = Array.from(tab);

function openTab(index) {
    listLink.forEach(content => content.classList.remove("bg-primary"));
    listTab.forEach(content => content.classList.remove("active"));
    listTab[index].classList.add("active");
    listLink[index].classList.add("bg-primary");
}
