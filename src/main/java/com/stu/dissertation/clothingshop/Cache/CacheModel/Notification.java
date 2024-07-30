package com.stu.dissertation.clothingshop.Cache.CacheModel;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String content;
    private List<String> hasReadedAttactments;
    private String role;
    private String type;
}
