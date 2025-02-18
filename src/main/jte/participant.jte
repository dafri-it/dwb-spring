@param de.dafri.dwb.view.ParticipantForm part

<label>
    First name:
    <input type="text" name="firstName" value="${part.getFirstName()}">
</label>
<label>
    Last name:
    <input type="text" name="lastName" value="${part.getLastName()}">
</label>
<label>
    Email:
    <input type="email" name="email" value="${part.getEmail()}">
</label>
<button hx-delete="/event/participant/${part.getId()}" hx-target="#participants">Delete</button>
