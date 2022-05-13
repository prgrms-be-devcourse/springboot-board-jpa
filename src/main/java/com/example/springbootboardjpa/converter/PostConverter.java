package com.example.springbootboardjpa.converter;

import com.example.springbootboardjpa.domain.Customer;
import com.example.springbootboardjpa.domain.Post;
import com.example.springbootboardjpa.dto.CustomerDto;
import com.example.springbootboardjpa.dto.PostDto;

import java.time.LocalDateTime;

public class PostConverter {
    public static PostDto toPostDto(Post post) {
        return new PostDto().builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .customer(PostConverter.toCustomerDto(post.getCustomer()))
                .created_at(post.getCratedAt())
                .created_by(post.getCreatedBy())
                .build();
    }

    public static Post toPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCustomer(PostConverter.toCustomer(postDto.getCustomer()));
        post.getCustomer().setPosts(post);
        post.setCratedAt(LocalDateTime.now());
        post.setCreatedBy(postDto.getCustomer().getName());
        return post;
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto().builder()
                .id(customer.getId())
                .name(customer.getName())
                .age(customer.getAge())
                .hobby(customer.getHobby())
                .created_at(customer.getCratedAt())
                .created_by(customer.getCreatedBy())
                .build();
    }

    public static Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        customer.setHobby(customerDto.getHobby());
        customer.setCratedAt(LocalDateTime.now());
        customer.setCreatedBy(customerDto.getName());
        return customer;
    }
}
