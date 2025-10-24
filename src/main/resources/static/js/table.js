document.addEventListener("DOMContentLoaded", () => {
    const ridInput = document.getElementById("rid");
    if (!ridInput) return;

    const rid = ridInput.value;
    fetch(`/api/reportBookings?rid=${encodeURIComponent(rid)}`)
        .then(res => res.json())
        .then(data => {
            renderTable(data);
        })
        .catch(err => {
            console.error("Failed to fetch bookings:", err);
            renderTable([]); // fallback row if empty
        });
});

function renderTable(data) {
    const thead = document.getElementById("table-head");
    const tbody = document.getElementById("table-body");
    thead.innerHTML = "";
    tbody.innerHTML = "";

    if (!data.length) {
        thead.innerHTML = "No available bookings in the given time period.";
        return;
    }

    let hiddenColumns = ["car", "rating", "type", "description"];
    let columns = Object.keys(data[0]).filter(col => !hiddenColumns.includes(col));

    // Build table header
    const headerRow = document.createElement("tr");
    columns.forEach(col => {
        const th = document.createElement("th");
        th.textContent = col;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    // Build table body
    data.forEach(row => {
        const tr = document.createElement("tr");
        columns.forEach(col => {
            const td = document.createElement("td");
            td.textContent = row[col] ?? "-";
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });

    // Initialize DataTable
    if ($.fn.DataTable.isDataTable('#myTable')) {
        $('#myTable').DataTable().destroy();
    }
    $('#myTable').DataTable({
        scrollY: '400px',
        scrollCollapse: true,
        paging: true
    });

    // Download button
    document.getElementById("downloadBtn").addEventListener("click", () => {
        let csv = columns.join(",") + "\n";
        data.forEach(row => {
            csv += columns.map(c => row[c] ?? "-").join(",") + "\n";
        });

        const blob = new Blob([csv], { type: 'text/csv' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = `report.csv`;
        a.click();
        URL.revokeObjectURL(url);
    });
}
