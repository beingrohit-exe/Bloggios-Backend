package com.smModule.postService.Implementation;

import com.smModule.postService.Constants.*;
import com.smModule.postService.Entity.Post;
import com.smModule.postService.Entity.Tag;
import com.smModule.postService.Exceptions.ApiException;
import com.smModule.postService.Payloads.*;
import com.smModule.postService.Repository.postRepository;
import com.smModule.postService.Service.postService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Author : Rohit Parihar
 * Date : 11/27/2022
 * Time : 6:25 PM
 * Class : postImplementation
 * Project : Bloggios-Backend
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class postImplementation implements postService {

    /**
     * Injecting PostRepository and WebClient via Constructor Injection
     */
    private final postRepository postRepository;
    private final WebClient.Builder webclient;

    /**
     * Loads data from Controller to save it Database with proper Validation
     *
     * @return postResponse
     * @throws ExecutionException
     * @throws InterruptedException
     * @RequestBody postRequest
     * @PathVariable userId
     */
    @Override
    public postResponse createPost(postRequest postRequest,
                                   String userId) {
        log.error("Entering category");
        Boolean categoryResult = webclient
                .build()
                .get()
                .uri(CategoryUri.CATEGORY_SERVICE + CategoryUri.CHECK_ID + postRequest.getCategoryId())
                .header(CategoryConstants.GATEWAY_HEADER, CategoryConstants.GATEWAY_SECRET)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        log.error("{}", categoryResult);
        if (Boolean.TRUE.equals(categoryResult)) {
            List<String> tags = postRequest.getTags();
            log.warn("Sending request to Tag");
            List<Tag> block = webclient
                    .build()
                    .post()
                    .uri(TagUri.TAG_SERVICE)
                    .header(TagConstants.GATEWAY_HEADER, TagConstants.GATEWAY_SECRET)
                    .body(BodyInserters.fromValue(tags))
                    .retrieve()
                    .bodyToFlux(Tag.class)
                    .collect(Collectors.toList())
                    .block();
            log.error("Got tags");
            Post post = Post
                    .builder()
                    .caption(postRequest.getCaption())
                    .userId(userId)
                    .categoryId(postRequest.getCategoryId())
                    .tags(block)
                    .dateCreated(new Date())
                    .isDeleted(false)
                    .isBlocked(false)
                    .build();
            Post savedPost = postRepository.save(post);
            log.info(PostConstants.POST_SAVED);
            return postResponse
                    .builder()
                    .id(savedPost.getPostId())
                    .message(PostConstants.POST_SAVED)
                    .isCreated(true)
                    .build();
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, PostConstants.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public postResponse updatePost(postRequest postRequest, String postId, String userId) {
        return null;
    }

    @Override
    public GetPostResponse getPost(String postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, PostConstants.POST_NOT_FOUND));
        List<Tag> tags = post.getTags();
        List<String> tagIdList = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        log.warn("Entering webclient");
        List<String> allTags = webclient
                .build()
                .post()
                .uri(TagUri.TAG_SERVICE + "name")
                .header(TagConstants.GATEWAY_HEADER, TagConstants.GATEWAY_SECRET)
                .body(BodyInserters.fromValue(tagIdList))
                .retrieve()
                .bodyToFlux(String.class)
                .collect(Collectors.toList())
                .block();
        log.warn("Complete");
        GetPostResponse postResponse = GetPostResponse
                .builder()
                .caption(post.getCaption())
                .tags(allTags)
                .createdBy(post.getUserId())
                .dateCreated(post.getDateCreated())
                .build();
        return postResponse;
    }

    @Override
    public pagedResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortId, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equals("ascending")){
            sort = Sort.by(sortId).ascending();
        }
        if (sortDirection.equals("descending")){
            sort = Sort.by(sortId).descending();
        }
        assert sort != null;
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pageContent = this.postRepository.findAll(page);
        List<Post> allPost = pageContent.getContent();
        List<AllPostResponse> postResponse = allPost.stream().map((e) -> AllPostResponse.builder()
                        .caption(e.getCaption())
                        .tags(e.getTags())
                        .userId(e.getUserId())
                        .categoryId(e.getCategoryId())
                        .dateCreated(e.getDateCreated())
                        .isEdited(e.getDateCreated() != null ? Boolean.TRUE : Boolean.FALSE)
                        .build())
                .collect(Collectors.toList());
        pagedResponse<Object> allResponse = pagedResponse
                .builder()
                .pageContents(Collections.singletonList(postResponse))
                .isFirst(pageContent.isFirst())
                .isLast(pageContent.isLast())
                .totalPages(pageContent.getTotalPages())
                .totalElements(pageContent.getTotalElements())
                .pageNumber(pageContent.getNumber())
                .pageSize(pageContent.getSize())
                .build();
        if (postResponse.isEmpty()){
            throw new ApiException(HttpStatus.NO_CONTENT, PostConstants.NO_POSTS);
        }
        return allResponse;
    }

    @Override
    public List<pagedResponse> getAllPostsByCategory(String categoryId, Long pageNumber, Long pageSize) {
        return null;
    }

    @Override
    public List<pagedResponse> getAllPostsByTags(String tagId, Long pageNumber, Long pageSize) {
        return null;
    }

    @Override
    public postResponse deletePost(String postId) {
        return null;
    }

    @Override
    public postResponse deletePostPermanently(String postId) {
        return null;
    }
}
