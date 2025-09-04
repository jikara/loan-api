package com.softmint.assembler;

import com.softmint.entity.Role;
import com.softmint.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    private final EntityLinks entityLinks;
    private final RoleModelAssembler roleModelAssembler;

    @Override
    @NonNull
    public EntityModel<User> toModel(@NonNull User user) {
        EntityModel<Role> roleModel = roleModelAssembler.toModel(user.getRole());
        EntityModel<User> model = EntityModel.of(user,
                entityLinks.linkToItemResource(User.class, user.getId()).withSelfRel(),
                roleModel.getRequiredLink("self").withRel("role")
        );

        if (user.getCreatedBy() != null) {
            Link createdByLink = entityLinks.linkToItemResource(User.class, user.getCreatedBy())
                    .withRel("createdBy");
            model.add(createdByLink);
        }
        if (user.getLastModifiedBy() != null) {
            Link modifiedByLink = entityLinks.linkToItemResource(User.class, user.getLastModifiedBy())
                    .withRel("lastModifiedBy");
            model.add(modifiedByLink);
        }
        return model;
    }


}

