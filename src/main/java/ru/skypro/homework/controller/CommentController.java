package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/ads/{id}/comments")
public class CommentController {
    private CommentService commentService;
    private AdController adController;

    public CommentController(CommentService commentService, AdController adController) {
        this.commentService = commentService;
        this.adController = adController;
    }

    @GetMapping()
    public ResponseEntity<CommentsDTO> getAllComments(@PathVariable int id,
                                                      Authentication authentication) {
        ExtendedAdDTO ad = adController.getAds(id, authentication).getBody();

        assert ad != null;
        CommentsDTO commentsDTO = commentService.getComments(ad.getPk());
        return ResponseEntity.ok(commentsDTO);
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> addComment(@PathVariable int id,
                                                 Authentication authentication,
                                                 @RequestBody CreateOrUpdateCommentDTO comment) {

        int adId = adController.getAds(id, authentication).getBody().getPk();

        CommentDTO commentDTO = commentService.addComment(adId, authentication.getName(), comment);

        return ResponseEntity.ok(commentDTO);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") int id,
                                                    @PathVariable("commentId") int commentId,
                                                    Authentication authentication,
                                                    @RequestBody CreateOrUpdateCommentDTO comment) {
        int adId = adController.getAds(id, authentication).getBody().getPk();

        CommentDTO commentDTO = commentService.updateComment(adId, commentId, comment);

        return ResponseEntity.ok(commentDTO);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") int id,
                                                    @PathVariable("commentId") int commentId,
                                                    Authentication authentication) {
        int adId = adController.getAds(id, authentication).getBody().getPk();

        commentService.deleteComment(adId, commentId);

        return ResponseEntity.ok().build();
    }


}
