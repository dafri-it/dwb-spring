@param de.dafri.dwb.view.RegisterForm form

@template.base-layout(content = @`
    <form action="/event/register" method="POST">
        <div id="participants">
        @for(de.dafri.dwb.view.ParticipantForm part: form.getParticipants())
            @template.participant(part = part)
        @endfor
        </div>
        <button hx-put="/event/participant" hx-target="#participants" hx-swap="beforeend">Add</button>
        <label>
            First name:
            <input type="text" name="firstName" value="${form.getFirstName()}">
        </label>
        <label>
            Last name:
            <input type="text" name="lastName" value="${form.getLastName()}">
        </label>
        <label>
            Email:
            <input type="email" name="email" value="${form.getEmail()}">
        </label>
        <button type="submit">Submit</button>
    </form>
`)