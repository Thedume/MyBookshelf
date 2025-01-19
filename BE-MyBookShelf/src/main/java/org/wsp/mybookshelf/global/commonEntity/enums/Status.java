<<<<<<< HEAD
package org.wsp.mybookshelf.global.commonEntity.enums;

public enum Status {
}
=======
package org.wsp.mybookshelf.global.commonEntity.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("활성"),
    UNACTIVE("비활성");

    private final String description; // 상태 설명 필드

    // 생성자
    Status(String description) {
        this.description = description;
    }
}
>>>>>>> ef4d9322cabfa11199b9fee8d65e5f14f3839b59
