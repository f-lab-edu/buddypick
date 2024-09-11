package com.buddypick.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "buddy_post")
public class BuddyPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "location", length = 400)
    private String location;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "current_participants")
    private Integer currentParticipants;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "hashtag", length = 200)
    private String hashtag;

    @Column(name = "has_license_yn")
    private Boolean hasLicenseYn;   //자격증유무

    @Column(name = "required_license_type")
    @Enumerated(EnumType.STRING)
    private License requiredLicenseType;     //자격증 종류

    @Column(name = "required_experience_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel requiredExperienceLevel;  //다이빙 경험수준

    @Column(name = "has_equipment_yn")
    private Boolean hasEquipmentYn;     // 장비대여 필요유무

    @Column(name = "has_vehicle_yn")
    private Boolean hasVehicleYn;      // 자차 필요유무

    @Column(name = "delete_yn")
    private Boolean deleteYn;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
