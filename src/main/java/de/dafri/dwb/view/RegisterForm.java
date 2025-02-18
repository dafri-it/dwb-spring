package de.dafri.dwb.view;

import java.util.LinkedList;
import java.util.List;

public class RegisterForm {
    private String firstName;
    private String lastName;
    private String email;
    private List<ParticipantForm> participants = new LinkedList<>();

    public RegisterForm(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public RegisterForm() {
    }

    public void addParticipant(ParticipantForm participant) {
        participants.add(participant);
    }

    public void removeParticipant(ParticipantForm participant) {
        participants.remove(participant);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ParticipantForm> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantForm> participants) {
        this.participants = participants;
    }
}
