package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads/{id}/comments")
public class CommentController {
    private final CommentService commentService;
    private final AdService adService;

    public CommentController(CommentService commentService, AdService adService) {
        this.commentService = commentService;
        this.adService = adService;
    }

    @Operation(
            tags = "Комментарии",
            summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentsDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<CommentsDTO> getAllComments(@PathVariable int id) {
        ExtendedAdDTO ad = adService.getAds(id);

        assert ad != null;
        CommentsDTO commentsDTO = commentService.getComments(ad.getPk());

        return ResponseEntity.ok(commentsDTO);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<CommentDTO> addComment(@PathVariable int id,
                                                 Authentication authentication,
                                                 @RequestBody CreateOrUpdateCommentDTO comment) {

        int adId = adService.getAds(id).getPk();

        CommentDTO commentDTO = commentService.addComment(adId, authentication.getName(), comment);

        return ResponseEntity.ok(commentDTO);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") int id,
                                                    @PathVariable("commentId") int commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO comment) {
        int adId = adService.getAds(id).getPk();

        CommentDTO commentDTO = commentService.updateComment(adId, commentId, comment);

        return ResponseEntity.ok(commentDTO);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentsDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") int id,
                                              @PathVariable("commentId") int commentId) {
        int adId = adService.getAds(id).getPk();

        commentService.deleteComment(adId, commentId);

        return ResponseEntity.ok().build();
    }


}
