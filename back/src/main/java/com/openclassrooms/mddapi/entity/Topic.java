package com.openclassrooms.mddapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "subscribers") // Exclude from ToString
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "subscriptions", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> subscribers = new HashSet<>();

    // Custom equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Topic))
            return false;
        Topic topic = (Topic) o;
        return id != null && id.equals(topic.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Helper methods
    public void addSubscription(User user) {
        subscribers.add(user);
        user.getSubscribedTopics().add(this);
    }

    public void removeSubscription(User user) {
        subscribers.remove(user);
        user.getSubscribedTopics().remove(this);
    }

    public boolean hasSubscription(User user) {
        return subscribers.contains(user);
    }
}