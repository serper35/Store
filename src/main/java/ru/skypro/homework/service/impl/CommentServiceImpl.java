package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserService userService;

    private AdService adService;
    private AdMapper adMapper;

    private AdRepository adRepository;


    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentsDTO getComments(int adId) {
        List<Comment> comments = commentRepository.getCommentsByAdId(adId);

        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> commentMapper.commentToCommentDTO(comment))
                .collect(Collectors.toList());

        return new CommentsDTO(commentDTOS.size(), commentDTOS);
    }

    @Override
    public CommentDTO addComment(int adId, String userName, CreateOrUpdateCommentDTO text) {
        Comment comment = new Comment();

        comment.setCreatedAt((int) Instant.now().toEpochMilli());
        comment.setText(text.getText());
        comment.setAd(adRepository.findById(adId).orElseThrow());
        comment.setAuthor(userService.getUser(userName));

        commentRepository.save(comment);

        return commentMapper.commentToCommentDTO(comment);
    }


}
