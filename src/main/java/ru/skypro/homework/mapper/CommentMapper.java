package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CommentMapper {

    protected UserService userService;
    protected AdService adService;

    @Mapping(target = "author", expression = "java(userService.findById(dto.getAuthor()))")
    @Mapping(target = "ad", expression = "java(adService.findById(dto.getPk()))")
    public abstract Comment commentDtoToComment(CommentDTO dto);

    @Mapping(target = "author", expression = "java(comment.getAuthor().getId())")
    @Mapping(target = "authorFirstName", expression = "java(comment.getAuthor().getFirstName())")
    @Mapping(target = "authorImage", source = "author",
            conditionExpression = "java(hasImage(comment.getAuthor()))",
            qualifiedByName = "imageToString")
    public abstract CommentDTO commentToCommentDTO(Comment comment);

    @Named("hasImage")
    boolean hasImage(User author) {
        return author.getImage() != null;
    }

    @Named("imageToString")
    String imageToString(User author) {
        return "/image/" + author.getImage().getId();
    }
}
